/**
 * MainVC.swift
 * created by Liviu Coman on 3/24/15.
 * copyright (c) 2015 LiviuComan. All rights reserved.
 */
import UIKit

/**
 * This is the VC that presents data to the user
 */
class MainVC: UIViewController, UITableViewDelegate, UITableViewDataSource, DataManagerProtocol {
    
    // outlets
    @IBOutlet weak var ArtifactTable: UITableView!
    @IBOutlet var RightSwipe: UISwipeGestureRecognizer!
    @IBOutlet var LeftSwipe: UISwipeGestureRecognizer!
    
    // controller variables I
    
    // data variables
    var mainVM: MainVM! = nil
    var art_i: Int = 0
    var artifacts: [Artifact] = []
    
    /**
     * required view controller initializer
     */
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
        self.mainVM = MainVM(artifact: nil)
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        // register nibs and such
        self.ArtifactTable.registerNib(UINib(nibName: "MainVC_TableViewCell", bundle: nil), forCellReuseIdentifier: "MainVC_TableViewCell_Id")
        
        // Do any additional setup after loading the view.
        DataManager.sharedInstance.data_delegate = self
        DataManager.sharedInstance.loadGeneration()
    }
    
    /**
     * Data manager delegate methods
     */
    func generationDidLoad(generation: Generation) {
        // setup data
        self.artifacts = generation.getArtifacts()
        self.art_i = 0
        setupViewWithViewManager()
    }
    
    /**
     * Function that sets-up or re-sets-up the view to match the current data
     * in the View Manager
     */
    func setupViewWithViewManager(){
        self.mainVM = MainVM(artifact: self.artifacts[art_i] as Artifact!)
        self.navigationItem.title = self.mainVM.getArtifactTitle()
        self.ArtifactTable.scrollRectToVisible(CGRectMake(0, 0, 1, 1), animated: false)
        self.ArtifactTable.reloadData()
    }
    
    /**
     * Table view delegate methods
     */
    func numberOfSectionsInTableView(tableView: UITableView) -> Int{
        return self.mainVM.getImageDescriptions().count
    }
    
//    func tableView(tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
//        return 0
//    }
    
    func tableView(tableView: UITableView, titleForHeaderInSection section: Int) -> String? {
        return self.mainVM.getImageDescription(index: section).getImgDescription()
    }
    
    func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return self.mainVM.getTilesArrayForImageSetImageData(index: section).count
    }
    
    func tableView(tableView: UITableView, heightForRowAtIndexPath indexPath: NSIndexPath) -> CGFloat {
        return self.mainVM.getTileInArrayCalcHeight(index: indexPath.section, tile: indexPath.row)
    }
    
    func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {

        var cell = tableView.dequeueReusableCellWithIdentifier("MainVC_TableViewCell_Id") as MainVC_TableViewCell?
        if (cell == nil){
            cell = MainVC_TableViewCell()
        }
        
        // nil image so it doesn't overlap and cause a jarring effect
        cell?.TileImage!.image = nil
        
        // load new image
        var uri_path = self.mainVM.getTileInArray(index: indexPath.section, tile: indexPath.row).getUriPath()
        asyncImageLoad(uri_path, { (myimage: UIImage, newframe: CGRect) -> () in
            if let cellToUpdate = tableView.cellForRowAtIndexPath(indexPath) as? MainVC_TableViewCell {
                cellToUpdate.TileImage!.frame = newframe
                cellToUpdate.TileImage!.image = myimage
            }
        })
    
        return cell!
    }
    
    /**
     * Swipe gesture
     */
    @IBAction func RightSwipeFunc(sender: AnyObject) {
        println("right swipe")
    }
    
    @IBAction func LeftSwipeFunc(sender: AnyObject) {
        art_i++;
        if (art_i < self.artifacts.count){
            setupViewWithViewManager()
        }
    }
    
    /*
    // MARK: - Mem warn 
    */
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */

}
