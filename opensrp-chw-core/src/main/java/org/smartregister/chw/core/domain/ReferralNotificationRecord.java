package org.smartregister.chw.core.domain;

public class ReferralNotificationRecord {

    private String clientBaseEntityId;
    private String clientName;
    private String clientDateOfBirth;
    private String phone;
    private String village;
    private String notificationDate;
    private String actionTaken;
    private String dangerSigns;
    private String selectedMethod;

    public ReferralNotificationRecord(String clientBaseEntityId) {
        this.clientBaseEntityId = clientBaseEntityId;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public void setClientDateOfBirth(String clientDateOfBirth) {
        this.clientDateOfBirth = clientDateOfBirth;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public void setNotificationDate(String notificationDate) {
        this.notificationDate = notificationDate;
    }

    public void setActionTaken(String actionTaken) {
        this.actionTaken = actionTaken;
    }

    public void setDangerSigns(String dangerSigns) {
        this.dangerSigns = dangerSigns;
    }

    public void setSelectedMethod(String selectedMethod) {
        this.selectedMethod = selectedMethod;
    }

    public String getClientName() {
        return clientName;
    }

    public String getClientDateOfBirth() {
        return clientDateOfBirth;
    }

    public String getPhone() {
        return phone;
    }

    public String getVillage() {
        return village;
    }

    public String getNotificationDate() {
        return notificationDate;
    }

    public String getActionTaken() {
        return actionTaken;
    }

    public String getDangerSigns() {
        return dangerSigns;
    }

    public String getSelectedMethod() {
        return selectedMethod;
    }

    public String getClientBaseEntityId() {
        return clientBaseEntityId;
    }

    public void setClientBaseEntityId(String clientBaseEntityId) {
        this.clientBaseEntityId = clientBaseEntityId;
    }
}
