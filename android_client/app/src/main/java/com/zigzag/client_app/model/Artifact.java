package com.zigzag.client_app.model;

import java.util.Collections;
import java.util.List;

public class Artifact extends Entity {
    private final String pageUrl;
    private final ArtifactSource artifactSource;
    private final String title;
    private final List<String> imageUrlPaths;

    public Artifact(EntityId id, String pageUrl, ArtifactSource artifactSource,
                    String title, List<String> imageUrlPaths) {
        super(id);
        this.pageUrl = pageUrl;
        this.artifactSource = artifactSource;
        this.title = title;
        this.imageUrlPaths = imageUrlPaths;
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public ArtifactSource getArtifactSource() {
        return artifactSource;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getImageUrlPaths() {
        return Collections.unmodifiableList(imageUrlPaths);
    }
}
