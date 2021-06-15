package org.smartregister.chw.core.model;

public class CoreFamilyMemberModel {
    private final String lastName;
    private final String baseEntityId;
    private final String familyBaseEntityId;
    private final String entityType;
    private String firstName;

    public CoreFamilyMemberModel(String lastName, String baseEntityId, String familyBaseEntityId, String entityType) {
        this.lastName = lastName;
        this.baseEntityId = baseEntityId;
        this.familyBaseEntityId = familyBaseEntityId;
        this.entityType = entityType;
    }

    public CoreFamilyMemberModel(String lastName, String firstName, String baseEntityId, String familyBaseEntityId, String entityType) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.baseEntityId = baseEntityId;
        this.familyBaseEntityId = familyBaseEntityId;
        this.entityType = entityType;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getEntityType() {
        return entityType;
    }

    public String getLastName() {
        return lastName;
    }

    public String getBaseEntityId() {
        return baseEntityId;
    }

    public String getFamilyBaseEntityId() {
        return familyBaseEntityId;
    }
}
