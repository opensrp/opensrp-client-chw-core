package org.smartregister.chw.core.model;

public class ChildModel {

    private String childFullName;
    private String dateOfBirth;
    private String baseEntityId;

    public ChildModel(String childFullName, String dateOfBirth) {
        this.childFullName = childFullName;
        this.dateOfBirth = dateOfBirth;
    }

    public ChildModel(String childFullName, String dob, String baseEntityId) {
        this.childFullName = childFullName;
        this.dateOfBirth = dob;
        this.baseEntityId = baseEntityId;
    }

    public String getChildFullName() {
        return childFullName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getBaseEntityId() {
        return baseEntityId;
    }

    public void setBaseEntityId(String baseEntityId) {
        this.baseEntityId = baseEntityId;
    }
}
