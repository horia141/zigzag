/**
 * TileCellSource.swift
 * created by Liviu Coman on 3/24/15.
 * copyright (c) 2015 LiviuComan. All rights reserved.
 */
import UIKit
import MediaPlayer

/**
 * model class that encompasses data for a tile cell view
 */
class TileCellSource: CellSource, TileCellViewProtocol{
    /**
     * private member vars
     */
    private var tile: Tile! = nil
    private var tileIsMovie: Bool = false
    private var video: Tile? = nil
    private var videoContainer: UIView? = nil
    private var mplayer: MPMoviePlayerController! = nil
    
    /**
     * custom constructor
     */
    init(tile: Tile, isMovie: Bool, video: Tile?){
        super.init()
        self.tile = tile
        self.tileIsMovie = isMovie
        self.video = video
    }
    
    /**
     * getters
     */
    func getTile() -> Tile{
        return self.tile
    }
    
    func getIsMovie() -> Bool {
        return self.tileIsMovie
    }
    
    func getVideo() -> Tile? {
        return self.video
    }
    
    func setVideoContainer(videoContainer: UIView) {
        self.videoContainer = videoContainer
    }
    
    func playVideo() {
        
        var urlstr = self.video?.getFullUriPath() as String!
        var url = NSURL(string: urlstr) as NSURL!
        
        mplayer = MPMoviePlayerController(contentURL: url)
        mplayer.view.frame = self.videoContainer!.frame
        mplayer.fullscreen = true
        mplayer.controlStyle = MPMovieControlStyle.None
        mplayer.scalingMode = .AspectFill
        mplayer.prepareToPlay()
        self.videoContainer!.addSubview(mplayer.view)
        mplayer.play()
    }
}
