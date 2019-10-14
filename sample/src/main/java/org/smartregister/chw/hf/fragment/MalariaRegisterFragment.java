package org.smartregister.chw.hf.fragment;

import org.smartregister.chw.core.fragment.CoreMalariaRegisterFragment;
import org.smartregister.chw.core.model.MalariaRegisterFragmentModel;
import org.smartregister.chw.hf.activity.MalariaProfileActivity;
import org.smartregister.chw.hf.presenter.MalariaRegisterFragmentPresenter;
import org.smartregister.chw.malaria.domain.MemberObject;
import org.smartregister.commonregistry.CommonPersonObjectClient;
import org.smartregister.view.activity.BaseRegisterActivity;

public class MalariaRegisterFragment extends CoreMalariaRegisterFragment {

    @Override
    protected void initializePresenter() {
        if (getActivity() == null) {
            return;
        }
        String viewConfigurationIdentifier = ((BaseRegisterActivity) getActivity()).getViewIdentifiers().get(0);
        presenter = new MalariaRegisterFragmentPresenter(this, new MalariaRegisterFragmentModel(), viewConfigurationIdentifier);
    }

    @Override
    protected void openProfile(CommonPersonObjectClient client) {
        MalariaProfileActivity.openProfile(getActivity(), new MemberObject(client), client);
    }
}
