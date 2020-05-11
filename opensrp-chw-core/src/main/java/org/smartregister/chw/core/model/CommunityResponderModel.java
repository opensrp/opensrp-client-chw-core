package org.smartregister.chw.core.model;

public class CommunityResponderModel {

    private String responderName;
    private String responderPhoneNumber;
    private String responderLocation;
    private String id;

    public CommunityResponderModel() {
    }

    public CommunityResponderModel(String responderName, String responderPhoneNumber, String responderLocation, String id) {
        this.id = id;
        this.responderName = responderName;
        this.responderPhoneNumber = responderPhoneNumber;
        this.responderLocation = responderLocation;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setResponderName(String responderName) {
        this.responderName = responderName;
    }

    public void setResponderPhoneNumber(String responderPhoneNumber) {
        this.responderPhoneNumber = responderPhoneNumber;
    }

    public void setResponderLocation(String responderLocation) {
        this.responderLocation = responderLocation;
    }

    public String getResponderName() {
        return responderName;
    }

    public String getResponderPhoneNumber() {
        return responderPhoneNumber;
    }

    public String getResponderLocation() {
        return responderLocation;
    }


}
