package org.smartregister.chw.core.fragment;

import org.smartregister.chw.core.R;
import org.smartregister.chw.core.activity.CoreFamilyProfileActivity;
import org.smartregister.family.fragment.BaseFamilyProfileMemberFragment;
import org.smartregister.view.contract.IView;

import java.util.HashMap;
import java.util.Set;

import timber.log.Timber;

public abstract class CoreFamilyProfileMemberFragment extends BaseFamilyProfileMemberFragment {

    @Override
    public abstract void initializeAdapter(Set<IView> visibleColumns, String familyHead, String primaryCaregiver);

    @Override
    protected abstract void initializePresenter();

    @Override
    public void setAdvancedSearchFormData(HashMap<String, String> hashMap) {
        Timber.v("setAdvancedSearchFormData");
    }

    @Override
    protected void onViewClicked(android.view.View view) {
        super.onViewClicked(view);
        int i = view.getId();
        if (i == R.id.patient_column) {
            if (view.getTag() != null && view.getTag(org.smartregister.family.R.id.VIEW_ID) == CLICK_VIEW_NORMAL) {
                ((CoreFamilyProfileActivity) getActivity()).goToProfileActivity(view, getArguments());
            }
        } else if (i == R.id.next_arrow && view.getTag() != null &&
                view.getTag(org.smartregister.family.R.id.VIEW_ID) == CLICK_VIEW_NEXT_ARROW) {
            ((CoreFamilyProfileActivity) getActivity()).goToProfileActivity(view, getArguments());
        }
    }

}
