package org.smartregister.chw.core.domain;

import java.util.List;

public class NotificationItem {

    private String clientBaseEntityId;
    private String title;
    private List<String> details;

    public NotificationItem(String title, List<String> details) {
        this.title = title;
        this.details = details;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getDetails() {
        return details;
    }

    public String getClientBaseEntityId() {
        return clientBaseEntityId;
    }

    public NotificationItem setClientBaseEntityId(String clientBaseEntityId) {
        this.clientBaseEntityId = clientBaseEntityId;
        return this;
    }
}
