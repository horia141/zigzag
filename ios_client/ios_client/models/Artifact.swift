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
    private var page_uri: String! = nil
    private var title: String! = nil
    private var artifact_source: ArtifactSource! = nil
    private var photo_descriptions: [PhotoDescription] = []
    private var artifact_date: NSTimeInterval = 0
    
    /**
     * custom constructor
     */
    init(id: EntityId, pageUri: String, artifactSource: ArtifactSource,
        title: String, photoDescription: [PhotoDescription], artifactDate: NSTimeInterval) {
            
        super.init(id: id)
        self.page_uri = pageUri
        self.title = title
        self.artifact_source = artifactSource
        self.photo_descriptions = photoDescription
        self.artifact_date = artifactDate
    }
    
    /**
     * getter methods
     */
    func getPageUri() -> String {
        return self.page_uri
    }
    
    func getTitle() -> String {
        return self.title
    }
    
    func getArtifactSource() -> ArtifactSource {
        return self.artifact_source
    }
    
    func getPhotoDescriptions() -> [PhotoDescription] {
        return self.photo_descriptions
    }
    
    func getArtifactDate() -> NSTimeInterval {
        return self.artifact_date
    }
}
