package com.zigzag.client_app.model;

import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Generation extends Entity {
    private final Date timeAdded;
     private final Date timeClosed;
    private final List<Artifact> artifacts;

    public Generation(EntityId entityId, Date timeAdded, Date timeClosed, List<Artifact> artifacts) {
        super(entityId);
        this.timeAdded = timeAdded;
        this.timeClosed = timeClosed;
        this.artifacts = artifacts;
    }

    public Date getTimeAdded() {
        return timeAdded;
    }

    public Date getTimeClosed() {
        return timeClosed;
    }

    public List<Artifact> getArtifacts() {
        return Collections.unmodifiableList(artifacts);
    }
}
