package org.smartregister.chw.core.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;

import com.vijay.jsonwizard.constants.JsonFormConstants;
import com.vijay.jsonwizard.domain.Form;

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

import static org.smartregister.chw.anc.util.NCUtils.getSyncHelper;
import static org.smartregister.util.Utils.getAllSharedPreferences;

public class CoreCommunityRespondersRegisterActivity extends AppCompatActivity {

    private ListView lv_country;
    public static final int TOOLBAR_ID = R.id.location_switching_toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community_responders);

        lv_country = findViewById(R.id.lv_country);
        Toolbar toolbar = findViewById(TOOLBAR_ID);
        setSupportActionBar(toolbar);
        findViewById(R.id.toggle_action_menu).setOnClickListener(v -> onClickReport(v));

    }


    public void onClickReport(View view) {
        if (view.getId() == R.id.toggle_action_menu) {
            NavigationMenu.getInstance(this, null, null).getDrawer().openDrawer(GravityCompat.START);
        }
    }

    @Override
    protected void onResume() {
        CommunityResponderRepository communityResponderRepository = CoreChwApplication.getInstance().communityResponderRepository();
        CommunityResponderCustomAdapter adapter = communityResponderRepository.readAllRespondersAdapter(getApplicationContext(), this);

        lv_country.setAdapter(adapter);
        registerForContextMenu(lv_country);
        lv_country.setAdapter(adapter);
        registerForContextMenu(lv_country);

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
            JSONObject form = FormUtils.getFormUtils().getFormJson(CoreConstants.JSON_FORM.COMMUNITY_RESPONDER_REGISTRATION_FORM);
            startActivityForResult(FormUtils.getStartFormActivity(form, this.getString(R.string.add_community_responder), this), org.smartregister.family.util.JsonFormUtils.REQUEST_CODE_GET_JSON);
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

            String baseEntityID = UUID.randomUUID().toString();
            baseEvent.setBaseEntityId(baseEntityID);

            CoreJsonFormUtils.tagSyncMetadata(org.smartregister.family.util.Utils.context().allSharedPreferences(), baseEvent);
            JSONObject eventJson = new JSONObject(CoreJsonFormUtils.gson.toJson(baseEvent));
            getSyncHelper().addEvent(baseEntityID, eventJson);

            long lastSyncTimeStamp = getAllSharedPreferences().fetchLastUpdatedAtDate(0);
            Date lastSyncDate = new Date(lastSyncTimeStamp);
            CoreChwApplication.getInstance().getClientProcessorForJava().processClient(getSyncHelper().getEvents(lastSyncDate, BaseRepository.TYPE_Unprocessed));
            getAllSharedPreferences().saveLastUpdatedAtDate(lastSyncDate.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startJsonActivity(CommunityResponderModel communityResponderModel) throws Exception {
        // Intent intent = new Intent(getContext(), Utils.metadata().familyMemberFormActivity);

        JSONObject jsonObject = org.smartregister.util.FormUtils.getInstance(CoreChwApplication.getInstance().getApplicationContext()).getFormJson(CoreConstants.JSON_FORM.COMMUNITY_RESPONDER_REGISTRATION_FORM);
        jsonObject.put("baseEntityId", communityResponderModel.getId());
        Map<String, String> valueMap = new HashMap<>();
        valueMap.put("responder_phone_number", communityResponderModel.getResponderPhoneNumber());
        valueMap.put("responder_name", communityResponderModel.getResponderName());
        valueMap.put("responder_gps", communityResponderModel.getResponderLocation());

        CoreJsonFormUtils.populateJsonForm(jsonObject, valueMap);

        Intent intent = new Intent(this, Utils.metadata().familyMemberFormActivity);
        intent.putExtra(Constants.JSON_FORM_EXTRA.JSON, jsonObject.toString());

        Form form = new Form();
        form.setActionBarBackground(org.smartregister.family.R.color.family_actionbar);
        form.setWizard(false);
        form.setSaveLabel("Save");
        intent.putExtra(JsonFormConstants.JSON_FORM_KEY.FORM, form);

        startActivityForResult(intent, JsonFormUtils.REQUEST_CODE_GET_JSON);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId() == R.id.lv_country) {
            AdapterView.AdapterContextMenuInfo info =
                    (AdapterView.AdapterContextMenuInfo) menuInfo;
            String country = ((TextView) info.targetView).getText().toString();
            menu.setHeaderTitle(country);

            String[] actions = getResources().getStringArray(R.array.responder_dialog_menu);
            for (int i = 0; i < actions.length; i++) {
                menu.add(Menu.NONE, i, i, actions[i]);
            }
        }
    }
}