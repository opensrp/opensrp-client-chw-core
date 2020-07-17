package org.smartregister.chw.core.model;

public class CoreFHIRBundleDataModel {
    private String muacValue;
    private String muacDisplay;
    private String ObservationValueCodableConceptCodingSystem;
    private String practitionerId;
    private String appLanguage;
    private String appLanguageDisplay;
    private String practitionerCommunicationCodingSystem;
    private String encounterIdentifierSystem;
    private String EncounterIdentifierValue;
    private String EncounterLocation;

    public String getMuacValue() {
        return muacValue;
    }

    public void setMuacValue(String muacValue) {
        this.muacValue = muacValue;
    }

    public String getMuacDisplay() {
        return muacDisplay;
    }

    public void setMuacDisplay(String muacDisplay) {
        this.muacDisplay = muacDisplay;
    }

    public String getObservationValueCodableConceptCodingSystem() {
        return ObservationValueCodableConceptCodingSystem;
    }

    public void setObservationValueCodableConceptCodingSystem(String observationValueCodableConceptCodingSystem) {
        ObservationValueCodableConceptCodingSystem = observationValueCodableConceptCodingSystem;
    }

    public String getPractitionerId() {
        return practitionerId;
    }

    public void setPractitionerId(String practitionerId) {
        this.practitionerId = practitionerId;
    }

    public String getAppLanguage() {
        return appLanguage;
    }

    public void setAppLanguage(String appLanguage) {
        this.appLanguage = appLanguage;
    }

    public String getAppLanguageDisplay() {
        return appLanguageDisplay;
    }

    public void setAppLanguageDisplay(String appLanguageDisplay) {
        this.appLanguageDisplay = appLanguageDisplay;
    }

    public String getPractitionerCommunicationCodingSystem() {
        return practitionerCommunicationCodingSystem;
    }

    public void setPractitionerCommunicationCodingSystem(String practitionerCommunicationCodingSystem) {
        this.practitionerCommunicationCodingSystem = practitionerCommunicationCodingSystem;
    }

    public String getEncounterIdentifierSystem() {
        return encounterIdentifierSystem;
    }

    public void setEncounterIdentifierSystem(String encounterIdentifierSystem) {
        this.encounterIdentifierSystem = encounterIdentifierSystem;
    }

    public String getEncounterIdentifierValue() {
        return EncounterIdentifierValue;
    }

    public void setEncounterIdentifierValue(String encounterIdentifierValue) {
        EncounterIdentifierValue = encounterIdentifierValue;
    }

    public String getEncounterLocation() {
        return EncounterLocation;
    }

    public void setEncounterLocation(String encounterLocation) {
        EncounterLocation = encounterLocation;
    }
}
