/**
 * TileData.swift
 * Created by Liviu Coman on 3/24/15.
 * Copyright (c) 2015 LiviuComan. All rights reserved.
 * based on design by HoriaComan
 */

import UIKit

/**
 * Tile Data class, that holds info for a single tile
 */
class TileData: NSObject {
    /**
     * private member variables
     */
    private var width: Int! = nil
    private var height: Int! = nil
    private var uriPath: String! = nil
    
    /**
     * custom constructor
     */
    init(width: Int, height: Int, uriPath: String){
        super.init()
        self.width = width
        self.height = height
        self.uriPath = uriPath
    }
    
    /**
     * custom getters
     */
    func getWidth() -> Int {
        return self.width
    }
    
    func getHeight() -> Int {
        return self.height
    }
    
    func getUriPath() -> String {
        return self.uriPath
    }
}
