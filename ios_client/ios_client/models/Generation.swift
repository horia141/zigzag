/**
 * Generation.swift
 * Created by Liviu Coman on 3/24/15.
 * Copyright (c) 2015 LiviuComan. All rights reserved.
 * based on design by HoriaComan
 */

import UIKit

/**
 * This is the generation class
 */
class Generation: Entity {
    /**
     * Generation private members, all initialized by default to nil or
     * empty (using implicit optionals)
     */
    private var timeAdded: String! = nil
    private var timeClosed: String! = nil
    private var artifacts: [Artifact] = []
    
    /**
     * custom constructor
     */
    init(id: EntityId, timeAdded: String, timeClosed: String, artifacts: [Artifact]){
        /**
         * call super class with entity ID
         */
        super.init(id: id)
        self.timeAdded = timeAdded
        self.timeClosed = timeClosed
        self.artifacts = artifacts
    }
    
    /**
     * getter methods
     */
    func getTimeAdded() -> String {
        return self.timeAdded
    }
    
    func getTimeClosed() -> String {
        return self.timeClosed
    }
    
    func getArtifacts() -> [Artifact] {
        return self.artifacts
    }
}
