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
    private var datetime_started: String! = nil
    private var datetime_ended: String! = nil
    private var datetime_started_ts: NSNumber! = nil
    private var datetime_ended_ts: NSNumber! = nil
    private var artifacts: [Artifact] = []
    
    /**
     * custom constructor
     */
    init(id: EntityId, timeStarted: String, timeEnded: String, timeStartedTS: NSNumber, timeEndedTS: NSNumber, artifacts: [Artifact]){
        /**
         * call super class with entity ID
         */
        super.init(id: id)
        self.datetime_started = timeStarted
        self.datetime_started_ts = timeStartedTS
        self.datetime_ended = timeEnded
        self.datetime_started_ts = timeEndedTS
        self.artifacts = artifacts
    }
    
    /**
     * getter methods
     */
    func getDateTimeStarted() -> String {
        return self.datetime_started
    }
    
    func getDateTimeStartedTS() -> NSNumber{
        return self.datetime_started_ts
    }
    
    func getDateTimeEnded() -> String {
        return self.datetime_ended
    }
    
    func getDateTimeEndedTS() -> NSNumber {
        return self.datetime_ended_ts
    }
    
    func getArtifacts() -> [Artifact] {
        return self.artifacts
    }
}
