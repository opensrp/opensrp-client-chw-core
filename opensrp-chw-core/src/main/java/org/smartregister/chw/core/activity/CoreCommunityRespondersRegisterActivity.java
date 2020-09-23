package org.smartregister.chw.core.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;

import org.json.JSONException;
import org.json.JSONObject;
import org.smartregister.chw.core.R;
import org.smartregister.chw.core.adapter.CommunityResponderCustomAdapter;
import org.smartregister.chw.core.application.CoreChwApplication;
import org.smartregister.chw.core.custom_views.NavigationMenu;
import org.smartregister.chw.core.model.CommunityResponderModel;
import org.smartregister.chw.core.repository.CommunityResponderRepository;
import org.smartregister.chw.core.utils.CoreConstants;
import org.smartregister.chw.core.utils.CoreJsonFormUtils;
import org.smartregister.chw.core.utils.FormUtils;
import org.smartregister.clientandeventmodel.Event;
import org.smartregister.family.util.Constants;
import org.smartregister.family.util.JsonFormUtils;
import org.smartregister.family.util.Utils;
import org.smartregister.repository.AllSharedPreferences;
import org.smartregister.repository.BaseRepository;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import timber.log.Timber;

import static org.smartregister.chw.anc.util.NCUtils.getSyncHelper;
import static org.smartregister.util.Utils.getAllSharedPreferences;

public class CoreCommunityRespondersRegisterActivity extends AppCompatActivity {

    private ListView communityRespondersListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community_responders);
        communityRespondersListView = findViewById(R.id.lv_responder);
        Toolbar toolbar = findViewById(R.id.location_switching_toolbar);
        setSupportActionBar(toolbar);
        findViewById(R.id.toggle_action_menu).setOnClickListener(v -> onClickDrawer(v));
    }

    public void onClickDrawer(View view) {
        if (view.getId() == R.id.toggle_action_menu) {
            NavigationMenu.getInstance(this, null, null).getDrawer().openDrawer(GravityCompat.START);
        }
    }

    @Override
    protected void onResume() {
        CommunityResponderRepository communityResponderRepository = CoreChwApplication.getInstance().communityResponderRepository();
        CommunityResponderCustomAdapter adapter = communityResponderRepository.readAllRespondersAdapter(getApplicationContext(), this);
        communityRespondersListView.setAdapter(adapter);
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_responder, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_add_responder) {
            CommunityResponderRepository repo = CoreChwApplication.getInstance().communityResponderRepository();
            if (repo.getRespondersCount() > 7) {
                Toast.makeText(this, getString(R.string.add_responder_max_message), Toast.LENGTH_LONG).show();
            } else {
                JSONObject form = FormUtils.getFormUtils().getFormJson(CoreConstants.JSON_FORM.COMMUNITY_RESPONDER_REGISTRATION_FORM);
                startActivityForResult(FormUtils.getStartFormActivity(form, this.getString(R.string.add_community_responder), this), JsonFormUtils.REQUEST_CODE_GET_JSON);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        try {
            String jsonString = data.getStringExtra(org.smartregister.family.util.Constants.JSON_FORM_EXTRA.JSON);
            AllSharedPreferences allSharedPreferences = getAllSharedPreferences();
            Event baseEvent = org.smartregister.chw.anc.util.JsonFormUtils.processJsonForm(allSharedPreferences, jsonString, CoreConstants.TABLE_NAME.COMMUNITY_RESPONDERS);
            baseEvent.setBaseEntityId(UUID.randomUUID().toString());
            CoreJsonFormUtils.tagSyncMetadata(Utils.context().allSharedPreferences(), baseEvent);
            JSONObject eventJson = new JSONObject(CoreJsonFormUtils.gson.toJson(baseEvent));
            getSyncHelper().addEvent(baseEvent.getBaseEntityId(), eventJson);

            FormUtils.processEvent();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startJsonActivity(CommunityResponderModel communityResponderModel, String baseEntityID) throws Exception {
        JSONObject jsonObject = org.smartregister.util.FormUtils.getInstance(CoreChwApplication.getInstance().getApplicationContext()).getFormJson(CoreConstants.JSON_FORM.COMMUNITY_RESPONDER_REGISTRATION_FORM);
        jsonObject.put("entity_id", baseEntityID);
        Map<String, String> valueMap = new HashMap<>();
        valueMap.put("id", baseEntityID);
        valueMap.put("responder_phone_number", communityResponderModel.getResponderPhoneNumber());
        valueMap.put("responder_name", communityResponderModel.getResponderName());
        valueMap.put("responder_gps", communityResponderModel.getResponderLocation());
        CoreJsonFormUtils.populateJsonForm(jsonObject, valueMap);
        Intent intent = new Intent(this, Utils.metadata().familyMemberFormActivity);
        intent.putExtra(Constants.JSON_FORM_EXTRA.JSON, jsonObject.toString());
        startActivityForResult(intent, JsonFormUtils.REQUEST_CODE_GET_JSON);
    }

    public void confirmPurgeResponder(String baseEntityID) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.remove_responder_dialog_title));
        builder.setMessage(getString(R.string.remove_responder_dialog_message));
        builder.setCancelable(true);
        builder.setPositiveButton(this.getString(R.string.remove_responder), (dialog, id) -> {
            try {
                addEvent(baseEntityID);
                FormUtils.processEvent();
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
            } catch (Exception e) {
                Timber.e(e, "CoreCommunityRespondersRegisterActivity --> confirmPurgeResponder");
            }
        });
        builder.setNegativeButton(this.getString(R.string.cancel), ((dialog, id) -> dialog.cancel()));
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void addEvent(String baseEntityID) {
        JSONObject form = FormUtils.getFormUtils().getFormJson(CoreConstants.JSON_FORM.COMMUNITY_RESPONDER_REGISTRATION_FORM);
        AllSharedPreferences allSharedPreferences = Utils.context().allSharedPreferences();
        Event baseEvent = org.smartregister.chw.anc.util.JsonFormUtils.processJsonForm(allSharedPreferences, form.toString(), CoreConstants.TABLE_NAME.COMMUNITY_RESPONDERS);
        baseEvent.setFormSubmissionId(UUID.randomUUID().toString());
        baseEvent.setBaseEntityId(baseEntityID);
        baseEvent.setEventType(CoreConstants.EventType.REMOVE_COMMUNITY_RESPONDER);
        CoreJsonFormUtils.tagSyncMetadata(allSharedPreferences, baseEvent);
        try {
            JSONObject eventJson = new JSONObject(CoreJsonFormUtils.gson.toJson(baseEvent));
            getSyncHelper().addEvent(baseEntityID, eventJson);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}