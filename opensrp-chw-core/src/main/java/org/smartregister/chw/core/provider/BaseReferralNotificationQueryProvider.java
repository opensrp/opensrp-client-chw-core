package org.smartregister.chw.core.provider;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.smartregister.chw.core.utils.CoreReferralUtils;
import org.smartregister.chw.core.utils.QueryConstant;

public class BaseReferralNotificationQueryProvider {
    /**
     * Return query to be used to select object_ids from the search table so that these objects_ids
     * are later used to retrieve the actual rows from the normal(non-FTS) table
     *
     * @param filters This is the search phrase entered in the search box
     * @return query string for getting object ids
     */
    @NonNull
    public String getObjectIdsQuery(@Nullable String filters) {
        return CoreReferralUtils.getFamilyMemberFtsSearchQuery(filters);
    }

    /**
     * Return query(s) to be used to perform the total count of register clients eg. If OPD combines records
     * in multiple tables then you can provide multiple queries with the result having a single row+column
     * and the counts will be summed up. Kindly try to use the search tables wherever possible.
     *
     * @return query string used for counting items
     */
    @NonNull
    public String[] countExecuteQueries() {
        return new String[]{QueryConstant.NOT_YET_DONE_REFERRAL_QUERY_COUNT};
    }

    /**
     * Return query to be used to retrieve the client details. This query should have a "WHERE base_entity_id IN (%s)" clause where
     * the comma-separated  base-entity-ids for the clients will be inserted into the query and later
     * executed
     *
     * @return query string used for displaying referral notifications
     */
    @NonNull
    public String mainSelectWhereIDsIn() {
      return QueryConstant.NOT_YET_DONE_REFERRAL_QUERY;
    }
}
