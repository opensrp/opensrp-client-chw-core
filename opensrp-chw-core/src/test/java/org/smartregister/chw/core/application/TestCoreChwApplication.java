package org.smartregister.chw.core.application;

import org.smartregister.family.domain.FamilyMetadata;

import java.util.ArrayList;

public class TestCoreChwApplication extends CoreChwApplication {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public FamilyMetadata getMetadata() {
        return null;
    }

    @Override
    public ArrayList<String> getAllowedLocationLevels() {
        return null;
    }

    @Override
    public ArrayList<String> getFacilityHierarchy() {
        return null;
    }

    @Override
    public String getDefaultLocationLevel() {
        return null;
    }
}
