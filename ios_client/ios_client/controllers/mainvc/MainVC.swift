/**
 * MainVC.swift
 * created by Liviu Coman on 3/24/15.
 * copyright (c) 2015 LiviuComan. All rights reserved.
 */
import UIKit

/**
 * This is the VC that presents data to the user
 */
class MainVC: UIViewController, UITableViewDelegate {
    
    /**
     * outlets
     */
    @IBOutlet weak var ArtifactTable: UITableView!
    @IBOutlet var RightSwipe: UISwipeGestureRecognizer!
    @IBOutlet var LeftSwipe: UISwipeGestureRecognizer!
    @IBOutlet weak var navigationBar: UINavigationBar!
    
    /**
     * controller data variables
     */
    var mainVM: MainVM! = nil
    
    /**
     * required view controller initializer
     */
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
        
        // become delegate
//        GenerationManager.sharedInstance.generation_delegate = self
        
        // setup the view manager
        mainVM = MainVM()
        mainVM.initVMWithData(GenerationManager.sharedInstance.getCurrentArtifact())
    }
    
    override func viewWillAppear(animated: Bool) {
        super.viewWillAppear(animated)
        
        // set title
//        self.navigationItem.title = "What's new?"
    }
    
    override func viewDidLayoutSubviews() {
        super.viewDidLayoutSubviews()
        
        // some more adjusting
        var frame = self.navigationBar.frame
        frame.origin.y = -25
        frame.size.height = 69
        self.navigationBar.frame = frame
        
        var frame2 = self.ArtifactTable.frame
        frame2.origin.y = 9
        frame2.size.height = UIScreen.mainScreen().bounds.size.height - 32
        self.ArtifactTable.frame = frame2
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        // register nibs and such
        self.ArtifactTable.registerNib(UINib(nibName: "TitleCellView", bundle: nil), forCellReuseIdentifier: "TitleCellViewId")
        self.ArtifactTable.registerNib(UINib(nibName: "PhotoCellView", bundle: nil), forCellReuseIdentifier: "PhotoCellViewId")
        self.ArtifactTable.registerNib(UINib(nibName: "VideoCellView", bundle: nil), forCellReuseIdentifier: "VideoCellViewId")
        
        // assign table data source
        self.ArtifactTable.dataSource = mainVM
        self.ArtifactTable.reloadData()
    }
    
    /**
     * Table view delegate methods
     */
    func tableView(tableView: UITableView, heightForRowAtIndexPath indexPath: NSIndexPath) -> CGFloat {
        var object = mainVM.getCellSourceAtIndexPath(indexPath) as CellSource
        return object.heightForRowAtIndexPath()
    }
    
    func tableView(tableView: UITableView, willDisplayCell cell: UITableViewCell, forRowAtIndexPath indexPath: NSIndexPath) {
        cell.backgroundColor = UIColor.clearColor()
    }
    
    /**
     * Swipe gesture
     */
    @IBAction func RightSwipeFunc(sender: AnyObject) {
        
        var orig_frame = self.ArtifactTable.frame as CGRect
        var new_frame = orig_frame
        new_frame.origin.x += UIScreen.mainScreen().bounds.width
        
        UIView.animateWithDuration(0.25,
            animations: {
                self.ArtifactTable.frame = new_frame
            },
            completion: {(value: Bool) in
                GenerationManager.sharedInstance.gotoPrevArtifact()
                self.mainVM.initVMWithData(GenerationManager.sharedInstance.getCurrentArtifact())
                self.ArtifactTable.frame = orig_frame
                self.ArtifactTable.scrollRectToVisible(CGRectMake(0, 0, 1, 1), animated: false)
                self.ArtifactTable.reloadData()
            }
        )
    }
    
    @IBAction func LeftSwipeFunc(sender: AnyObject) {
        
        var orig_frame = self.ArtifactTable.frame as CGRect
        var new_frame = orig_frame
        new_frame.origin.x -= UIScreen.mainScreen().bounds.width
        
        UIView.animateWithDuration(0.25,
            animations: {
                self.ArtifactTable.frame = new_frame
            },
            completion: {(value: Bool) in
                GenerationManager.sharedInstance.gotoNextArtifact()
                self.mainVM.initVMWithData(GenerationManager.sharedInstance.getCurrentArtifact())
                self.ArtifactTable.frame = orig_frame
                self.ArtifactTable.scrollRectToVisible(CGRectMake(0, 0, 1, 1), animated: false)
                self.ArtifactTable.reloadData()
            }
        )
    }
    
    
    @IBAction func openMenu(sender: AnyObject) {
        // do nothing now
        NSNotificationCenter.defaultCenter().postNotificationName(MenuVC.MenuVCOpenMenuNotification(), object: nil)
    }
    
    @IBAction func shareArtifact(sender: AnyObject) {
        var artifact = self.mainVM.vm_artifact
        var pdata = (self.mainVM.vm_artifact.getPhotoDescriptions().first as PhotoDescription!).getPhotoData() as PhotoData!
        
        // determine the actual URL
        var url = "" as String
        if (pdata.getType() == PhotoData.getImagePhotoDataType()){
            url = (pdata as! ImagePhotoData).getTiles().first?.getFullUriPath() as String!
        }
        else {
            url = (pdata as! VideoPhotoData).getFirstFrame().getFullUriPath() as String!
        }
        
        // load image async
        asyncImageLoad(url, { (image: UIImage, size: CGRect) in
            shareInfo(
                title: "Look what I found on PicJar today",
                text: artifact.getTitle(),
                url: artifact.getPageUri(),
                img: image)
        })
    }
    
    /*
     * MARK: - Mem warn
     */
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
//    /**
//     * Generation manager delegate methods
//     */
//    func didGetNextGeneration() {
//        GenerationManager.sharedInstance.getCurrentArtifact()
//        mainVM.initVMWithData(GenerationManager.sharedInstance.getCurrentArtifact())
//        ArtifactTable.reloadData()
//    }
    
//    func didFailedToGetNextGeneration() {
//        // alert somehow
//    }
}
