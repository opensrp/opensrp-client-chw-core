package org.smartregister.chw.core.interactor;

import android.content.Context;

import org.jetbrains.annotations.Nullable;
import org.joda.time.LocalDate;
import org.smartregister.chw.anc.domain.MemberObject;
import org.smartregister.chw.anc.domain.Visit;
import org.smartregister.chw.anc.model.BaseUpcomingService;
import org.smartregister.chw.anc.util.VisitUtils;
import org.smartregister.chw.core.contract.CoreHivProfileContract;
import org.smartregister.chw.core.contract.CoreIndexContactProfileContract;
import org.smartregister.chw.core.dao.AlertDao;
import org.smartregister.chw.core.dao.AncDao;
import org.smartregister.chw.core.dao.PNCDao;
import org.smartregister.chw.core.dao.VisitDao;
import org.smartregister.chw.core.utils.CoreConstants;
import org.smartregister.chw.hiv.contract.BaseHivProfileContract;
import org.smartregister.chw.hiv.contract.BaseIndexContactProfileContract;
import org.smartregister.chw.hiv.dao.HivDao;
import org.smartregister.chw.hiv.domain.HivIndexContactObject;
import org.smartregister.chw.hiv.domain.HivMemberObject;
import org.smartregister.chw.hiv.interactor.BaseHivProfileInteractor;
import org.smartregister.chw.hiv.interactor.BaseIndexContactProfileInteractor;
import org.smartregister.chw.tb.dao.TbDao;
import org.smartregister.dao.AbstractDao;
import org.smartregister.domain.Alert;
import org.smartregister.domain.AlertStatus;
import org.smartregister.repository.AllSharedPreferences;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import timber.log.Timber;

public class CoreHivIndexContactProfileInteractor extends BaseIndexContactProfileInteractor implements CoreIndexContactProfileContract.Interactor {
    private Context context;

    public CoreHivIndexContactProfileInteractor(Context context) {
        this.context = context;
    }

    public MemberObject toMember(HivIndexContactObject hivIndexContactObject) {
        MemberObject res = new MemberObject();
        res.setBaseEntityId(hivIndexContactObject.getBaseEntityId());
        res.setFirstName(hivIndexContactObject.getFirstName());
        res.setLastName(hivIndexContactObject.getLastName());
        res.setMiddleName(hivIndexContactObject.getMiddleName());
        res.setDob(hivIndexContactObject.getDob());
        return res;
    }

    @Override
    public void createReferralEvent(AllSharedPreferences allSharedPreferences, String jsonString, String entityID) throws Exception {
        //TODO implement this
    }

    @Override
    public void updateProfileHivStatusInfo(@Nullable HivIndexContactObject hivIndexContactObject, @Nullable BaseIndexContactProfileContract.InteractorCallback callback) {

    }
}