/**
 * VideoPhotoData.swift
 * Created by Liviu Coman on 3/24/15.
 * Copyright (c) 2015 LiviuComan. All rights reserved.
 * based on design by HoriaComan
 */

import UIKit

class VideoPhotoData: PhotoData {
    /**
     * private member variables
     */
    private var first_frame: Tile! = nil
    private var video: Tile! = nil
    private var frames_per_sec: Int = 0
    private var time_between_frames_ms: Int = 0
    
    /**
     * custom constructor
     */
    init(firstFrame: Tile, video: Tile, framesPerSec: Int, timeBetweenFramesMs: Int){
        super.init(type: PhotoData.getVideoPhotoDataType())
        self.first_frame = firstFrame
        self.video = video
        self.frames_per_sec = framesPerSec
        self.time_between_frames_ms = timeBetweenFramesMs
    }
    
    /**
     * getter methods
     */
    func getFirstFrame() -> Tile{
        return self.first_frame
    }
    
    func getVideo() -> Tile{
        return self.video
    }
    
    func getFramesPerSec() -> Int{
        return self.frames_per_sec
    }
    
    func getTimeBetweenFramesMs() -> Int{
        return self.time_between_frames_ms
    }
}