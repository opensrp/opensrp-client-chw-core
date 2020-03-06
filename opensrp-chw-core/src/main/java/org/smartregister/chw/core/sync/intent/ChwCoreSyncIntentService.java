package org.smartregister.chw.core.sync.intent;

import android.content.Intent;
import android.util.Pair;

import com.google.common.collect.Lists;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.smartregister.CoreLibrary;
import org.smartregister.domain.FetchStatus;
import org.smartregister.domain.Response;
import org.smartregister.domain.Task;
import org.smartregister.sync.helper.ECSyncHelper;
import org.smartregister.sync.intent.SyncIntentService;

import java.util.List;

import timber.log.Timber;

public abstract class ChwCoreSyncIntentService extends SyncIntentService {
    public static final String SYNC_URL = "/rest/event/sync-by-base-entity-ids";

    public ChwCoreSyncIntentService(String name) {
        super(name);
    }

    public synchronized void fetchMissingEventsRetry(final int count, List<Task> tasksWithMissingClientsEvents) {
        Timber.i("Tasks with missing clients and/or events = %s", new Gson().toJson(tasksWithMissingClientsEvents));
        List<List<Task>> tasksWithMissingClientsEventsBatches = Lists.partition(tasksWithMissingClientsEvents, 1000);
        for (List<Task> tasksList : tasksWithMissingClientsEventsBatches) {
            JSONArray baseEntityIdsArray = new JSONArray();
            for (Task task : tasksList) {
                baseEntityIdsArray.put(task.getForEntity());
            }
            try {
                if (getHttpAgent() == null) {
                    complete(FetchStatus.fetchedFailed);
                    return;
                }

                Response resp = fetchClientEventsByBaseEntityIds(baseEntityIdsArray);
                if (resp.isTimeoutError() || resp.isUrlError()) {
                    FetchStatus.fetchedFailed.setDisplayValue(resp.status().displayValue());
                    complete(FetchStatus.fetchedFailed);
                    return;
                } else if (resp.isFailure()) {
                    fetchMissingEventsFailed(count, tasksWithMissingClientsEvents);
                    return;
                }


                JSONArray jsonArray = new JSONArray((String) resp.payload());

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    int eCount = fetchNumberOfEvents(jsonObject);
                    if (eCount < 0) {
                        fetchMissingEventsFailed(count, tasksWithMissingClientsEvents);
                        return;
                    } else {
                        processClientEvent(jsonObject); //Process the client and his/her events
                    }
                }
            } catch (Exception e) {
                Timber.e(e, "Fetch Retry Exception:  %s", e.getMessage());
                fetchMissingEventsFailed(count, tasksWithMissingClientsEvents);
            }
        }
        complete(FetchStatus.fetched);

    }

    public Response fetchClientEventsByBaseEntityIds(JSONArray baseEntityIds) throws Exception {
        String baseUrl = CoreLibrary.getInstance().context().
                configuration().dristhiBaseURL();
        if (baseUrl.endsWith("/")) {
            baseUrl = baseUrl.substring(0, baseUrl.lastIndexOf("/"));
        }

        String url = baseUrl + SYNC_URL;
        JSONObject syncParams = new JSONObject();
        syncParams.put("baseEntityIds", baseEntityIds);
        syncParams.put("withFamilyEvents", true);
        return getHttpAgent().postWithJsonResponse(url, syncParams.toString());
    }

    private void processClientEvent(JSONObject jsonObject) {
        final ECSyncHelper ecSyncUpdater = ECSyncHelper.getInstance(getContext());
        final Pair<Long, Long> serverVersionPair = getMinMaxServerVersions(jsonObject);
        boolean isSaved = ecSyncUpdater.saveAllClientsAndEvents(jsonObject);
        if (isSaved) {
            processClient(serverVersionPair);
        }
    }

    public void fetchMissingEventsFailed(int count, List<Task> tasksWithMissingClientsEvents) {
        if (count < CoreLibrary.getInstance().getSyncConfiguration().getSyncMaxRetries()) {
            int newCount = count + 1;
            fetchMissingEventsRetry(newCount, tasksWithMissingClientsEvents);
        } else {
            complete(FetchStatus.fetchedFailed);
        }
    }


    @Override
    abstract protected void onHandleIntent(Intent intent);

    @Override
    protected int fetchNumberOfEvents(JSONObject jsonObject) {
        int count = -1;
        final String events = "events";
        try {
            if (jsonObject != null && jsonObject.has(events)) {
                count = jsonObject.getJSONArray(events).length();
            }
        } catch (JSONException e) {
            Timber.e(e);
        }
        return count;
    }
}

