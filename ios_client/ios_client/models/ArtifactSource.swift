/**
 * ArtifactSource.swift
 * Created by Liviu Coman on 3/24/15.
 * Copyright (c) 2015 LiviuComan. All rights reserved.
 * based on design by HoriaComan
 */

import UIKit

class ArtifactSource: Entity {
    /**
     * private artifact source variables
     */
    private var start_page_uri: String! = nil
    private var name: String! = nil
    
    /**
     * custom constructor
     */
    init(id: EntityId, startPageUri: String, name: String) {
        super.init(id: id)
        self.start_page_uri = startPageUri
        self.name = name
    }
    
    /**
     * getters
     */
    func getStartPageUri() -> String {
        return self.start_page_uri;
    }
    
    func getName() -> String {
        return self.name;
    }
}
