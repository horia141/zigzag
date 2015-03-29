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
    private var startPageUrl: String! = nil
    private var name: String! = nil
    private var timeAdded: NSDate! = nil
    
    /**
     * custom constructor
     */
    init(id: EntityId, startPageUrl: String, name: String, timeAdded: NSDate) {
        super.init(id: id)
        self.startPageUrl = startPageUrl
        self.name = name
        self.timeAdded = timeAdded
    }
    
    /**
     * getters
     */
    func getStartPageUrl() -> String {
        return self.startPageUrl;
    }
    
    func getName() -> String {
        return self.name;
    }
    
    func getTimeAdded() -> NSDate {
        return self.timeAdded;
    }
}
