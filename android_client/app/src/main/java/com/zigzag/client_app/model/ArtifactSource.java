package com.zigzag.client_app.model;

import java.util.Date;

public class ArtifactSource extends Entity {
    private final String startPageUrl;
    private final String name;
    private final Date timeAdded;

    public ArtifactSource(EntityId id, String startPageUrl, String name, Date timeAdded) {
        super(id);
        this.startPageUrl = startPageUrl;
        this.name = name;
        this.timeAdded = timeAdded;
    }

    public String getStartPageUrl() {
        return startPageUrl;
    }

    public String getName() {
        return name;
    }

    public Date getTimeAdded() {
        return timeAdded;
    }
}
