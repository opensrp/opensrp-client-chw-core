package org.smartregister.chw.core.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.smartregister.chw.core.R;
import org.smartregister.chw.core.contract.CoreCertificationRegisterFragmentContract;
import org.smartregister.chw.core.custom_views.NavigationMenu;
import org.smartregister.chw.core.model.CoreCertificationRegisterFragmentModel;
import org.smartregister.chw.core.presenter.CoreCertificationRegisterFragmentPresenter;
import org.smartregister.chw.core.provider.CoreCertificationRegisterProvider;
import org.smartregister.commonregistry.CommonPersonObjectClient;
import org.smartregister.cursoradapter.RecyclerViewPaginatedAdapter;
import org.smartregister.cursoradapter.SmartRegisterQueryBuilder;
import org.smartregister.domain.FetchStatus;
import org.smartregister.family.fragment.NoMatchDialogFragment;
import org.smartregister.receiver.SyncStatusBroadcastReceiver;
import org.smartregister.util.Utils;
import org.smartregister.view.activity.BaseRegisterActivity;

import java.util.HashMap;
import java.util.Set;

import timber.log.Timber;

public class CoreCertificationRegisterFragment extends BaseChwRegisterFragment implements CoreCertificationRegisterFragmentContract.View {

    public static final String CLICK_VIEW_NORMAL = "click_view_normal";
    public static final String CLICK_CERTIFICATION_STATUS = "click_view_certification_status";
    private static final String DUE_FILTER_TAG = "PRESSED";

    protected View view;
    protected View dueOnlyLayout;
    protected boolean dueFilterActive = false;

    @Override
    public void setupViews(View view) {
        super.setupViews(view);
        this.view = view;

        dueOnlyLayout = view.findViewById(R.id.due_only_layout);
        dueOnlyLayout.setVisibility(View.VISIBLE);
        dueOnlyLayout.setOnClickListener(registerActionHandler);
    }

    @Override
    protected void initializePresenter() {
        if (getActivity() == null) {
            return;
        }

        presenter = new CoreCertificationRegisterFragmentPresenter(this, new CoreCertificationRegisterFragmentModel(), null);
    }

    @Override
    public void initializeAdapter(Set<org.smartregister.configurableviews.model.View> visibleColumns) {
        CoreCertificationRegisterProvider coreCertificationRegisterProvider = new CoreCertificationRegisterProvider(requireActivity(), visibleColumns, registerActionHandler, paginationViewHandler);
        clientAdapter = new RecyclerViewPaginatedAdapter(null, coreCertificationRegisterProvider, context().commonrepository(this.tablename));
        clientAdapter.setCurrentlimit(20);
        clientsView.setAdapter(clientAdapter);
    }

    @Override
    public void setUniqueID(String s) {
        if (getSearchView() != null) {
            getSearchView().setText(s);
        }
    }

    @Override
    public void setAdvancedSearchFormData(HashMap<String, String> hashMap) {
        // Todo advanced search
    }

    @Override
    protected void onResumption() {
        if (dueFilterActive && dueOnlyLayout != null) {
            dueFilter(dueOnlyLayout);
        } else {
            super.onResumption();
        }
    }

    @Override
    protected String getMainCondition() {
        return presenter().getMainCondition();
    }

    @Override
    protected String getDefaultSortQuery() {
        return presenter().getDefaultSortQuery();
    }

    @Override
    protected void startRegistration() {
        // Override start form if module requires registration
    }

    @Override
    protected void onViewClicked(View view) {
        if (getActivity() == null) {
            return;
        }

        if (view.getId() == R.id.due_only_layout) {
            toggleFilterSelection(view);
        } else if (view.getTag() != null && view.getTag(R.id.VIEW_ID) == CLICK_CERTIFICATION_STATUS) {
            if (view.getTag() instanceof CommonPersonObjectClient) {
                showUpdateForm((CommonPersonObjectClient) view.getTag());
            }
        }
    }

    @Override
    public void onSyncInProgress(FetchStatus fetchStatus) {
        if (!SyncStatusBroadcastReceiver.getInstance().isSyncing() && (FetchStatus.fetched.equals(fetchStatus) || FetchStatus.nothingFetched.equals(fetchStatus)) && dueFilterActive && dueOnlyLayout != null) {
            dueFilter(dueOnlyLayout);
            Utils.showShortToast(getActivity(), getString(R.string.sync_complete));
            refreshSyncProgressSpinner();
        } else {
            super.onSyncInProgress(fetchStatus);
        }
    }

    @Override
    public void onSyncComplete(FetchStatus fetchStatus) {
        if (!SyncStatusBroadcastReceiver.getInstance().isSyncing() && (FetchStatus.fetched.equals(fetchStatus) || FetchStatus.nothingFetched.equals(fetchStatus)) && (dueFilterActive && dueOnlyLayout != null)) {
            dueFilter(dueOnlyLayout);
            Utils.showShortToast(getActivity(), getString(R.string.sync_complete));
            refreshSyncProgressSpinner();
        } else {
            super.onSyncComplete(fetchStatus);
        }

        if (syncProgressBar != null) {
            syncProgressBar.setVisibility(View.GONE);
        }
        if (syncButton != null) {
            syncButton.setVisibility(View.GONE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Toolbar toolbar = view.findViewById(R.id.register_toolbar);
        toolbar.setContentInsetsAbsolute(0, 0);
        toolbar.setContentInsetsRelative(0, 0);
        toolbar.setContentInsetStartWithNavigation(0);
        NavigationMenu.getInstance(getActivity(), null, toolbar);
    }

    @Override
    protected void refreshSyncProgressSpinner() {
        super.refreshSyncProgressSpinner();
        if (syncButton != null) {
            syncButton.setVisibility(View.GONE);
        }
    }

    public void showUpdateForm(CommonPersonObjectClient client) {
        Intent intent = getUpdateIntent(client);
        if (intent != null)
            startActivity(intent);
    }

    public Intent getUpdateIntent(CommonPersonObjectClient client) {
        return null;
    }

    public void toggleFilterSelection(View dueOnlyLayout) {
        if (dueOnlyLayout != null) {
            if (dueOnlyLayout.getTag() == null) {
                dueFilterActive = true;
                dueFilter(dueOnlyLayout);
            } else if (dueOnlyLayout.getTag().toString().equals(DUE_FILTER_TAG)) {
                dueFilterActive = false;
                normalFilter(dueOnlyLayout);
            }
        }
    }

    private void normalFilter(View dueOnlyLayout) {
        filter(searchText(), "", presenter().getMainCondition());
        dueOnlyLayout.setTag(null);
        switchViews(dueOnlyLayout, false);
    }

    private void dueFilter(View dueOnlyLayout) {
        filter(searchText(), "", getDueFilterCondition());
        dueOnlyLayout.setTag(DUE_FILTER_TAG);
        switchViews(dueOnlyLayout, true);
    }

    protected String getDueFilterCondition() {
        return presenter().getDueFilterCondition();
    }

    protected void filter(String filterString, String joinTableString, String mainConditionString) {
        filters = filterString;
        joinTable = joinTableString;
        mainCondition = mainConditionString;
        filterAndSortExecute();
    }

    protected void filterAndSortExecute() {
        filterandSortExecute(countBundle());
    }

    private String searchText() {
        return (getSearchView() == null) ? "" : getSearchView().getText().toString();
    }

    protected TextView getDueOnlyTextView(View dueOnlyLayout) {
        return dueOnlyLayout.findViewById(R.id.due_only_text_view);
    }

    private void switchViews(View dueOnlyLayout, boolean isPress) {
        if (isPress) {
            getDueOnlyTextView(dueOnlyLayout).setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_due_filter_on, 0);
        } else {
            getDueOnlyTextView(dueOnlyLayout).setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_due_filter_off, 0);

        }
    }

    @Override
    public CoreCertificationRegisterFragmentContract.Presenter presenter() {
        return (CoreCertificationRegisterFragmentContract.Presenter) presenter;
    }

    @Override
    protected int getToolBarTitle() {
        return R.string.certification_clients;
    }

    @Override
    public void showNotFoundPopup(String uniqueId) {
        if (getActivity() == null) {
            return;
        }
        NoMatchDialogFragment.launchDialog((BaseRegisterActivity) requireActivity(), DIALOG_TAG, uniqueId);
    }

    @NotNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (id == LOADER_ID) {// Returns a new CursorLoader
            return new CursorLoader(requireActivity()) {
                @Override
                public Cursor loadInBackground() {
                    // Count query
                    final String COUNT = "count_execute";
                    if (args != null && args.getBoolean(COUNT)) {
                        countExecute();
                    }
                    String query = filterandSortQuery();
                    return commonRepository().rawCustomQueryForAdapter(query);
                }
            };
        }// An invalid id was passed in
        return null;
    }


    @Override
    public void countExecute() {
        Cursor c = null;
        try {
            c = commonRepository().rawCustomQueryForAdapter(getCountSelect());
            c.moveToFirst();
            clientAdapter.setTotalcount(c.getInt(0));

            clientAdapter.setCurrentlimit(20);
            clientAdapter.setCurrentoffset(0);
        } catch (Exception e) {
            Timber.e(e);
        } finally {
            if (c != null) {
                c.close();
            }
        }
    }

    private String getCountSelect() {
        SmartRegisterQueryBuilder sqb = new SmartRegisterQueryBuilder(countSelect);

        String query = countSelect;
        try {
            if (StringUtils.isNotBlank(filters))
                query = sqb.addCondition(((CoreCertificationRegisterFragmentPresenter) presenter()).getFilterString(filters));

            if (dueFilterActive)
                query = sqb.addCondition(((CoreCertificationRegisterFragmentPresenter) presenter()).getDueCondition());
            query = sqb.Endquery(query);
        } catch (SQLException e) {
            Timber.e(e);
        }

        return query;
    }

    private String filterandSortQuery() {
        SmartRegisterQueryBuilder sqb = new SmartRegisterQueryBuilder(mainSelect);

        String query = "";
        try {
            if (StringUtils.isNotBlank(filters))
                sqb.addCondition(((CoreCertificationRegisterFragmentPresenter) presenter()).getFilterString(filters));

            if (dueFilterActive)
                sqb.addCondition(((CoreCertificationRegisterFragmentPresenter) presenter()).getDueCondition());
            query = sqb.orderbyCondition(Sortqueries);
            query = sqb.Endquery(sqb.addlimitandOffset(query, clientAdapter.getCurrentlimit(), clientAdapter.getCurrentoffset()));
        } catch (SQLException e) {
            Timber.e(e);
        }

        return query;
    }
}
