/**
 * GenerationManager.swift
 * created by Liviu Coman on 3/24/15.
 * copyright (c) 2015 LiviuComan. All rights reserved.
 */

import UIKit

/**
 * Generation manager delegate
 */
protocol GenerationManagerProtocol {
    func didGetNextGeneration()
    func didFailedToGetNextGeneration()
}

/**
 * This class manages generations for the user
 */
class GenerationManager: NSObject, ConnectionManagerProtocol {
    
    /**
     * private constants
     */
    private let GEN_URL = "http://horia141.com:9000/api/v1/nextgen?output=json&from=" as String
    private let IMG_URL = "http://horia141.com:9001/" as String
    
    /**
     * connection variables
     */
    private var conn_manager: ConnectionManager! = nil
    
    /**
     * Generation manager variables
     */
    private var cur_generation: Generation! = nil
    private var gen_i: Int = 0
    private var artifacts: [Artifact] = []
    private var cur_artifact: Artifact! = nil
    private var art_i: Int = 0
    private var max_art_i: Int = 0
    var generation_delegate : GenerationManagerProtocol! = nil
    
    /**
     * singleton method
     */
    class var sharedInstance: GenerationManager {
        struct Singleton {
            static let instance = GenerationManager()
        }
        
        return Singleton.instance
    }
    
    /**
     * Override init function
     */
    override init(){
        super.init()
        // init the connection manager
        self.conn_manager = ConnectionManager()
        self.conn_manager.connection_delegate = self
    }
    
    /**
     * function that for now just takes the latest generation
     */
    func downloadNextGeneration(){
        gen_i++;
        var localURL = GEN_URL+"\(gen_i)"
        self.conn_manager.makeRequest(localURL)
    }
    
    
    
    /**
     * Function that gets the current existing generation
     */
    func getCurrentArtifact() -> Artifact{
        return self.cur_artifact
    }
    
    func gotoNextArtifact(){
        art_i++;
        self.cur_artifact = self.artifacts[art_i]
        
        if (art_i >= max_art_i - 5){
            downloadNextGeneration()
        }
    }
    
    func gotoPrevArtifact(){
        if (art_i > 0){
            art_i--;
            // get prev artifact
            self.cur_artifact = self.artifacts[art_i]
        }
    }
    
    func getImgURL() -> String{
        return self.IMG_URL
    }
    
    /**
     * Connection Manager Protocol functions
     */
    func getData(packet: NSData) {
        // this bit here parses a generation using a specific parser
        var parser = GenerationParser(data: packet) as GenerationParser
        parser.startParsingData()
        self.cur_generation = parser.getParsedGeneration()
        self.artifacts += self.cur_generation.getArtifacts()
        self.max_art_i += self.cur_generation.getArtifacts().count
        
        // just when loading
        if (self.cur_artifact == nil){
            self.cur_artifact = self.artifacts.first! as Artifact
            self.generation_delegate.didGetNextGeneration()
        }
    }
    
    func failedToGetData() {
        self.generation_delegate.didFailedToGetNextGeneration()
    }
}
