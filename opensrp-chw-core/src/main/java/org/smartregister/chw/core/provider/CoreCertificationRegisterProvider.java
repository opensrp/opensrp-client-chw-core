package org.smartregister.chw.core.provider;

import static org.smartregister.chw.core.utils.CoreConstants.FEMALE;
import static org.smartregister.chw.core.utils.CoreConstants.MALE;
import static org.smartregister.chw.core.utils.Utils.getDuration;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.apache.commons.lang3.text.WordUtils;
import org.smartregister.chw.core.R;
import org.smartregister.chw.core.fragment.CoreCertificationRegisterFragment;
import org.smartregister.chw.core.holders.FooterViewHolder;
import org.smartregister.chw.core.holders.RegisterViewHolder;
import org.smartregister.chw.core.utils.ChildDBConstants;
import org.smartregister.commonregistry.CommonPersonObjectClient;
import org.smartregister.cursoradapter.RecyclerViewProvider;
import org.smartregister.family.fragment.BaseFamilyRegisterFragment;
import org.smartregister.family.util.DBConstants;
import org.smartregister.family.util.Utils;
import org.smartregister.view.contract.SmartRegisterClient;
import org.smartregister.view.contract.SmartRegisterClients;
import org.smartregister.view.dialog.FilterOption;
import org.smartregister.view.dialog.ServiceModeOption;
import org.smartregister.view.dialog.SortOption;
import org.smartregister.view.viewholder.OnClickFormLauncher;

import java.text.MessageFormat;
import java.util.Set;

public class CoreCertificationRegisterProvider implements RecyclerViewProvider<RegisterViewHolder> {

    public final LayoutInflater inflater;

    private Set<org.smartregister.configurableviews.model.View> visibleColumns;

    private View.OnClickListener onClickListener;
    private View.OnClickListener paginationClickListener;

    private Context context;

    public CoreCertificationRegisterProvider(Context context, Set visibleColumns, View.OnClickListener onClickListener, View.OnClickListener paginationClickListener) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.visibleColumns = visibleColumns;
        this.onClickListener = onClickListener;
        this.paginationClickListener = paginationClickListener;
        this.context = context;
    }

    @Override
    public void getView(Cursor cursor, SmartRegisterClient client, RegisterViewHolder viewHolder) {
        CommonPersonObjectClient pc = (CommonPersonObjectClient) client;
        if (visibleColumns != null && visibleColumns.isEmpty()) {
            populatePatientColumn(pc, client, viewHolder);
        }
    }

    @Override
    public void getFooterView(RecyclerView.ViewHolder viewHolder, int currentPageCount, int totalPageCount, boolean hasNext, boolean hasPrevious) {
        FooterViewHolder footerViewHolder = (FooterViewHolder) viewHolder;
        footerViewHolder.pageInfoView.setText(
                MessageFormat.format(context.getString(org.smartregister.R.string.str_page_info), currentPageCount,
                        totalPageCount));

        footerViewHolder.nextPageView.setVisibility(hasNext ? View.VISIBLE : View.INVISIBLE);
        footerViewHolder.previousPageView.setVisibility(hasPrevious ? View.VISIBLE : View.INVISIBLE);

        footerViewHolder.nextPageView.setOnClickListener(paginationClickListener);
        footerViewHolder.previousPageView.setOnClickListener(paginationClickListener);
    }

    @Override
    public SmartRegisterClients updateClients(FilterOption villageFilter, ServiceModeOption serviceModeOption, FilterOption searchFilter, SortOption sortOption) {
        return null;
    }

    @Override
    public void onServiceModeSelected(ServiceModeOption serviceModeOption) {
        // Abstract method implementation
    }

    @Override
    public OnClickFormLauncher newFormLauncher(String formName, String entityId, String metaData) {
        return null;
    }

    @Override
    public LayoutInflater inflater() {
        return inflater;
    }

    @Override
    public RegisterViewHolder createViewHolder(ViewGroup parent) {
        View view = inflater.inflate(R.layout.adapter_certification_register_list_row, parent, false);

        return new RegisterViewHolder(view);
    }

    @Override
    public RecyclerView.ViewHolder createFooterHolder(ViewGroup parent) {
        View view = inflater.inflate(R.layout.smart_register_pagination, parent, false);
        return new FooterViewHolder(view);
    }

    @Override
    public boolean isFooterViewHolder(RecyclerView.ViewHolder viewHolder) {
        return viewHolder instanceof FooterViewHolder;
    }

    protected void populatePatientColumn(CommonPersonObjectClient pc, SmartRegisterClient client, RegisterViewHolder viewHolder) {

        String parentFirstName = Utils.getValue(pc.getColumnmaps(), ChildDBConstants.KEY.FAMILY_FIRST_NAME, true);
        String parentLastName = Utils.getValue(pc.getColumnmaps(), ChildDBConstants.KEY.FAMILY_LAST_NAME, true);
        String parentMiddleName = Utils.getValue(pc.getColumnmaps(), ChildDBConstants.KEY.FAMILY_MIDDLE_NAME, true);

        StringBuilder parentName = new StringBuilder();
        parentName.append(context.getResources().getString(R.string.care_giver_initials)).append(": ").append(org.smartregister.util.Utils.getName(parentFirstName, parentMiddleName + " " + parentLastName));
        String firstName = Utils.getValue(pc.getColumnmaps(), DBConstants.KEY.FIRST_NAME, true);
        String middleName = Utils.getValue(pc.getColumnmaps(), DBConstants.KEY.MIDDLE_NAME, true);
        String lastName = Utils.getValue(pc.getColumnmaps(), DBConstants.KEY.LAST_NAME, true);
        String childName = org.smartregister.util.Utils.getName(firstName, middleName + " " + lastName);

        fillValue(viewHolder.textViewParentName, WordUtils.capitalize(parentName.toString()));

        String dobString = getDuration(Utils.getValue(pc.getColumnmaps(), DBConstants.KEY.DOB, false));
        StringBuilder childNameWithDOB = new StringBuilder();
        childNameWithDOB.append(WordUtils.capitalize(childName)).append(", ").append(WordUtils.capitalize(Utils.getTranslatedDate(dobString, context)));

        fillValue(viewHolder.textViewChildName, childNameWithDOB.toString());
        setAddressAndGender(pc, viewHolder);

        addStatusButtonClickListener(client, viewHolder);

    }

    public void setAddressAndGender(CommonPersonObjectClient pc, RegisterViewHolder viewHolder) {
        String address = Utils.getValue(pc.getColumnmaps(), ChildDBConstants.KEY.FAMILY_HOME_ADDRESS, true);
        String genderKey = Utils.getValue(pc.getColumnmaps(), DBConstants.KEY.GENDER, true);
        String gender = "";
        if (genderKey.equalsIgnoreCase(MALE)) {
            gender = context.getString(R.string.male);
        } else if (genderKey.equalsIgnoreCase(FEMALE)) {
            gender = context.getString(R.string.female);
        }
        StringBuilder addressGender = new StringBuilder();
        addressGender.append(address).append(" \u00B7 ").append(gender);
        fillValue(viewHolder.textViewAddressGender, addressGender.toString());
    }

    protected static void fillValue(TextView v, String value) {
        if (v != null) {
            v.setText(value);
        }
    }

    public void addStatusButtonClickListener(SmartRegisterClient client, RegisterViewHolder viewHolder) {
        View statusButton = viewHolder.dueButton;
        statusButton.setOnClickListener(onClickListener);
        statusButton.setTag(client);
        statusButton.setTag(R.id.VIEW_ID, CoreCertificationRegisterFragment.CLICK_CERTIFICATION_STATUS);

        viewHolder.dueButtonLayout.setOnClickListener(v -> viewHolder.dueButton.performClick());
    }

    public void setReceivedButtonColor(Context context, Button dueButton) {
        updateButton(dueButton, context.getString(R.string.certificate_received),
                context.getResources().getColor(R.color.certificate_received_green), 0);
    }

    public void setUpdateStatusButtonColor(Context context, Button dueButton) {
        updateButton(dueButton, context.getString(R.string.update_status),
                context.getResources().getColor(R.color.update_certificate_yellow), R.drawable.update_cert_status_btn);

    }

    // Todo -> When is this set?
    public void setStatusUpdated(Context context, Button dueButton) {
        updateButton(dueButton, context.getString(R.string.status_updated),
                context.getResources().getColor(R.color.black), 0);
    }

    public void setNotReceivedButtonColor(Context context, Button dueButton) {
        updateButton(dueButton, context.getString(R.string.certificate_not_received),
                context.getResources().getColor(R.color.certificate_not_received_red), R.drawable.certificate_not_received_btn);
    }

    public void updateButton(Button dueButton, String text, int colourId, int backgroundResource) {
        dueButton.setTextColor(colourId);
        dueButton.setText(text);
        dueButton.setBackgroundResource(backgroundResource);
    }
}
