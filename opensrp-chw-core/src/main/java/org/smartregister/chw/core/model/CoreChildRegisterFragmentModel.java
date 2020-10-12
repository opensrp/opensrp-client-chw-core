package org.smartregister.chw.core.model;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.smartregister.chw.core.contract.CoreChildRegisterFragmentContract;
import org.smartregister.chw.core.utils.ConfigHelper;
import org.smartregister.chw.core.utils.CoreChildUtils;
import org.smartregister.configurableviews.ConfigurableViewsLibrary;
import org.smartregister.configurableviews.model.RegisterConfiguration;
import org.smartregister.cursoradapter.SmartRegisterQueryBuilder;
import org.smartregister.domain.Response;
import org.smartregister.domain.ResponseStatus;
import org.smartregister.family.util.Utils;
import org.smartregister.view.contract.IField;
import org.smartregister.view.contract.IView;
import org.smartregister.view.contract.IViewConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import timber.log.Timber;

public class CoreChildRegisterFragmentModel implements CoreChildRegisterFragmentContract.Model {
    @Override
    public RegisterConfiguration defaultRegisterConfiguration() {
        return ConfigHelper.defaultRegisterConfiguration(Utils.context().applicationContext());
    }

    @Override
    public IViewConfiguration getViewConfiguration(String viewConfigurationIdentifier) {
        return ConfigurableViewsLibrary.getInstance().getConfigurableViewsHelper().getViewConfiguration(viewConfigurationIdentifier);
    }

    @Override
    public Set<IView> getRegisterActiveColumns(String viewConfigurationIdentifier) {
        return ConfigurableViewsLibrary.getInstance().getConfigurableViewsHelper().getRegisterActiveColumns(viewConfigurationIdentifier);
    }

    @Override
    public String countSelect(String tableName, String mainCondition) {
        return null;
    }

    @Override
    public String mainSelect(String tableName, String mainCondition) {
        return null;
    }

    @Override
    public String countSelect(String tableName, String mainCondition, String familyMemberTableName) {
        SmartRegisterQueryBuilder countQueryBuilder = new SmartRegisterQueryBuilder();
        countQueryBuilder.selectInitiateMainTableCounts(tableName);
        countQueryBuilder.customJoin("INNER JOIN " + familyMemberTableName + " ON  " + tableName + ".base_entity_id =  " + familyMemberTableName + ".base_entity_id");
        return countQueryBuilder.mainCondition(mainCondition);
    }

    @Override
    public String mainSelect(String tableName, String familyName, String familyMemberName, String mainCondition) {
        return CoreChildUtils.mainSelectRegisterWithoutGroupby(tableName, familyName, familyMemberName, mainCondition);
    }

    @Override
    public String getFilterText(List<IField> list, String filterTitle) {
        List<IField> filterList = list;
        if (filterList == null) {
            filterList = new ArrayList<>();
        }

        String filter = filterTitle;
        if (filter == null) {
            filter = "";
        }
        return "<font color=#727272>" + filter + "</font> <font color=#f0ab41>(" + filterList.size() + ")</font>";
    }

    @Override
    public String getSortText(IField sortField) {
        String sortText = "";
        if (sortField != null) {
            if (StringUtils.isNotBlank(sortField.getDisplayName())) {
                sortText = "(Sort: " + sortField.getDisplayName() + ")";
            } else if (StringUtils.isNotBlank(sortField.getDbAlias())) {
                sortText = "(Sort: " + sortField.getDbAlias() + ")";
            }
        }
        return sortText;
    }

    @Override
    public JSONArray getJsonArray(Response<String> response) {
        try {
            if (response.status().equals(ResponseStatus.success)) {
                return new JSONArray(response.payload());
            }
        } catch (Exception e) {
            Timber.e(e);
        }
        return null;
    }
}
