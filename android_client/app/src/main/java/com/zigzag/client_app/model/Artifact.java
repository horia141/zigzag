package com.zigzag.client_app.model;

import java.util.Collections;
import java.util.List;

public class Artifact extends Entity {
    private final String pageUrl;
    private final ArtifactSource artifactSource;
    private final String title;
    private final List<ImageDescription> imagesDescription;

    public Artifact(EntityId id, String pageUrl, ArtifactSource artifactSource,
                    String title, List<ImageDescription> imagesDescription) {
        super(id);
        this.pageUrl = pageUrl;
        this.artifactSource = artifactSource;
        this.title = title;
        this.imagesDescription = imagesDescription;
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

    public List<ImageDescription> getImagesDescription() {
        return Collections.unmodifiableList(imagesDescription);
    }
}
