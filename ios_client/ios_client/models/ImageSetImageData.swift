/**
 * ImageSetImageData.swift
 * Created by Liviu Coman on 3/24/15.
 * Copyright (c) 2015 LiviuComan. All rights reserved.
 * based on design by HoriaComan
 */

import UIKit

/**
 * The ImageSet Image Data class is a class that implements the ImageData
 * protocol (Swift's/ObjC's way of implementing abstract base classes)
 */
class ImageSetImageData: NSObject, ImageData {
    /**
     * private variables
     */
    private var fullImageDesc: TileData! = nil
    private var tilesDesc: [TileData] = []
    private var totalHeight: Int = 0
    
    /**
     * custom init
     */
    init(fullImageDesc: TileData, tilesDesc: [TileData]) {
        super.init()
        self.fullImageDesc = fullImageDesc
        self.tilesDesc = tilesDesc
        self.totalHeight = 0
        
        for td in self.tilesDesc{
            self.totalHeight += td.getHeight()
        }
    }
    
    /**
     * custom getters
     */
    func getFulImageDesc() -> TileData {
        return self.fullImageDesc
    }
    
    func getTilesDesc() -> [TileData] {
        return self.tilesDesc
    }
    
    /**
     * Implementation of ImageData protocol functions
     * - getUriPathsToFetsh()
     * - getTotalHeight()
     */
    func getUriPathsToFetch() -> [String] {
        var uriPaths: [String] = []
        
        for td in self.tilesDesc{
            uriPaths.append(td.getUriPath())
        }
        
        return uriPaths
    }
    
    func getTotalHeight() -> Int {
        return self.totalHeight
    }
}
