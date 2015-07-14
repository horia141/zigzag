/**
 * ImagePhotoData.swift
 * Created by Liviu Coman on 3/24/15.
 * Copyright (c) 2015 LiviuComan. All rights reserved.
 * based on design by HoriaComan
 */

import UIKit

class ImagePhotoData: PhotoData {
    /**
     * private member variables
     */
    private var tiles: [Tile] = []
    
    /**
     * custom constructor
     */
    init(tiles: [Tile]){
        super.init(type: PhotoData.getImagePhotoDataType())
        self.tiles = tiles
    }
    
    /**
     * getter methods
     */
    func getTiles() -> [Tile]{
        return self.tiles
    }
}