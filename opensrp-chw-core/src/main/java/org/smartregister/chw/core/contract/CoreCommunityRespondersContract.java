package org.smartregister.chw.core.contract;

import android.content.Context;

import org.smartregister.chw.anc.contract.BaseAncRespondersCallDialogContract;
import org.smartregister.chw.core.model.CommunityResponderModel;

import java.util.List;

public interface CoreCommunityRespondersContract {

    interface View {

        Presenter presenter();

        Context getContext();

        void refreshCommunityResponders(List<CommunityResponderModel> communityResponderModelList);
    }

    interface Presenter {

        View getView();

        void updateCommunityResponders(List<CommunityResponderModel> communityResponderModelList);

        void addCommunityResponder(String jsonString);

        void removeCommunityResponder(String baseEntityId);
    }

    interface Model extends BaseAncRespondersCallDialogContract.Model {

    }

    interface Interactor {

        List<CommunityResponderModel> getAllCommunityResponders();

        void addCommunityResponder(String jsonString);

        void removeCommunityResponder(String baseEntityId);

    }

    interface InteractorCallBack {
        void updateCommunityRespondersList(List<CommunityResponderModel> communityResponderModelList);
    }
}
