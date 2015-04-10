/**
 * Artifact.swift
 * Created by Liviu Coman on 3/24/15.
 * Copyright (c) 2015 LiviuComan. All rights reserved.
 * based on design by HoriaComan
 */


import UIKit

class Artifact: Entity {
    /**
     * private member variables
     */
    private var pageUrl: String! = nil
    private var title: String! = nil
    private var artifactSource: ArtifactSource! = nil
    private var imageDescription: [ImageDescription] = []
    
    /**
     * custom constructor
     */
    init(id: EntityId, pageUrl: String, artifactSource: ArtifactSource,
        title: String, imageDescription: [ImageDescription]) {
            
        super.init(id: id)
        self.pageUrl = pageUrl
        self.title = title
        self.artifactSource = artifactSource
        self.imageDescription = imageDescription
    }
    
    /**
     * getter methods
     */
    func getPageUrl() -> String {
        return self.pageUrl
    }
    
    func getTitle() -> String {
        return self.title
    }
    
    func getArtifactSource() -> ArtifactSource {
        return self.artifactSource
    }
    
    func getImagesDescription() -> [ImageDescription] {
        return self.imageDescription
    }
}
