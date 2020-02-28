package org.smartregister.chw.core.sync.intent;

import android.content.Intent;
import android.util.Pair;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;
import org.smartregister.CoreLibrary;
import org.smartregister.SyncConfiguration;
import org.smartregister.domain.FetchStatus;
import org.smartregister.domain.Response;
import org.smartregister.domain.Task;
import org.smartregister.sync.helper.ECSyncHelper;
import org.smartregister.sync.intent.SyncIntentService;

import java.util.List;

import timber.log.Timber;

public abstract class ChwCoreSyncIntentService extends SyncIntentService {

    public ChwCoreSyncIntentService(String name) {
        super(name);
    }

    public synchronized void fetchMissingEventsRetry(final int count, List<Task> tasksWithMissingClientsEvents) {
        Timber.i("Tasks with missing clients and/or events = %s", new Gson().toJson(tasksWithMissingClientsEvents));

        //TODO implement an additional endpoint to query client events using multiple baseEntityIds at once and remove the use of a loop
        for (Task task : tasksWithMissingClientsEvents) {
            try {
                if (getHttpAgent() == null) {
                    complete(FetchStatus.fetchedFailed);
                    return;
                }

                Response resp = fetchClientEventsByBaseEntityId(task.getForEntity());
                if (resp.isTimeoutError() || resp.isUrlError()) {
                    FetchStatus.fetchedFailed.setDisplayValue(resp.status().displayValue());
                    complete(FetchStatus.fetchedFailed);
                    return;
                } else if (resp.isFailure()) {
                    fetchMissingEventsFailed(count, tasksWithMissingClientsEvents);
                    return;
                }

                int eCount;
                JSONObject jsonObject = new JSONObject();
                if (resp.payload() == null) {
                    eCount = 0;
                } else {
                    jsonObject = new JSONObject((String) resp.payload());
                    eCount = fetchNumberOfEvents(jsonObject);
                    Timber.i("Parse Network Event Count: %s", eCount);
                }

                if (eCount < 0) {
                    fetchMissingEventsFailed(count, tasksWithMissingClientsEvents);
                    return;
                } else {
                    processMissingEventsObject(jsonObject,count,tasksWithMissingClientsEvents);
                }
            } catch (Exception e) {
                Timber.e(e, "Fetch Retry Exception:  %s", e.getMessage());
                fetchMissingEventsFailed(count, tasksWithMissingClientsEvents);
            }
        }

    }

    public Response fetchClientEventsByBaseEntityId(String baseEntityId) throws Exception {
        String baseUrl = CoreLibrary.getInstance().context().
                configuration().dristhiBaseURL();
        if (baseUrl.endsWith("/")) {
            baseUrl = baseUrl.substring(0, baseUrl.lastIndexOf("/"));
        }
        SyncConfiguration configs = CoreLibrary.getInstance().getSyncConfiguration();
        String url = baseUrl + SYNC_URL;
        Response resp;
        if (configs.isSyncUsingPost()) {
            JSONObject syncParams = new JSONObject();
            syncParams.put("baseEntityId", baseEntityId);
            syncParams.put("serverVersion", 0);
            resp = getHttpAgent().postWithJsonResponse(url, syncParams.toString());
        } else {
            url += "?baseEntityId=" + baseEntityId + "&serverVersion=0";
            resp = getHttpAgent().fetch(url);
        }
        return resp;
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

    public void processMissingEventsObject(JSONObject jsonObject,int count, List<Task> tasksWithMissingClientsEvents) throws Exception{
        //fetch clients family registration event
        JSONArray clients = jsonObject.has("clients") ? jsonObject.getJSONArray("clients") : new JSONArray();

        if (clients.length() == 1) {
            JSONArray familyObject = clients.getJSONObject(0)
                    .getJSONObject("relationships").has("family")
                    ? clients.getJSONObject(0).getJSONObject("relationships")
                    .getJSONArray("family") : new JSONArray();

            for (int i = 0; i < familyObject.length(); i++) {
                Response familyResponse = fetchClientEventsByBaseEntityId(familyObject.getString(i));
                if (familyResponse.isFailure() && !familyResponse.isUrlError() && !familyResponse.isTimeoutError()) {
                    fetchMissingEventsFailed(count, tasksWithMissingClientsEvents);
                } else {
                    processClientEvent(new JSONObject((String) familyResponse.payload())); //process the family events
                }

            }
        }

        processClientEvent(jsonObject);//Process the client and his/her events
        complete(FetchStatus.fetched);
    }

    @Override
    abstract protected void onHandleIntent(Intent intent);
}

