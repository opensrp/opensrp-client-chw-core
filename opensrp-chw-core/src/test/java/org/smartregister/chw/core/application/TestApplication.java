package org.smartregister.chw.core.application;

import org.mockito.Mockito;
import org.smartregister.family.domain.FamilyMetadata;
import org.smartregister.receiver.SyncStatusBroadcastReceiver;

import java.util.ArrayList;

/**
 * @author rkodev
 */
public class TestApplication extends CoreChwApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        SyncStatusBroadcastReceiver.init(this);

    }

    @Override
    public FamilyMetadata getMetadata() {
        return Mockito.mock(FamilyMetadata.class);
    }

    @Override
    public ArrayList<String> getAllowedLocationLevels() {
        return new ArrayList<>();
    }

    @Override
    public ArrayList<String> getFacilityHierarchy() {
        return new ArrayList<>();
    }

    @Override
    public String getDefaultLocationLevel() {
        return "default";
    }

}
