/**
 * MenuVC.swift
 * created by Liviu Coman on 3/24/15.
 * copyright (c) 2015 LiviuComan. All rights reserved.
 */

import UIKit

/**
 * The class that handles the menu
 */
class MenuVC: UIViewController, UITableViewDelegate {
    /**
     * outlets
     */
    @IBOutlet var MenuTable: UITableView!

    /**
     * Private variables
     */
    private var menu_vm: MenuVM! = nil
    private var current_vc: UIViewController! = nil
    private var menuOpened: Bool = false
    
    /**
     * init
     */
    required init(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
        
        // init menu vm
        menu_vm = MenuVM()
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
//        var imgv = UIImageView(frame: UIScreen.mainScreen().bounds)
//        imgv.image = UIImage(named: "background_2")
//        self.MenuTable.addSubview(imgv)
//        self.view.bringSubviewToFront(self.MenuTable)
        
        // register nibs and such
        self.MenuTable.registerNib(UINib(nibName: "MenuHeaderCellView", bundle: nil), forCellReuseIdentifier: "MenuHeaderCellViewId")
        self.MenuTable.registerNib(UINib(nibName: "MenuRowCellView", bundle: nil), forCellReuseIdentifier: "MenuRowCellViewId")
        
        // register notification
        NSNotificationCenter.defaultCenter().addObserver(self, selector: "openMenu", name: MenuVC.MenuVCOpenMenuNotification(), object: nil)
        
        // Do any additional setup after loading the view.
        MenuTable.dataSource = menu_vm
        MenuTable.reloadData()
    }
    
    override func viewWillAppear(animated: Bool) {
        super.viewWillAppear(animated)
        // setup navigation status
        self.navigationController?.navigationBarHidden = true
        
        var indexpath = NSIndexPath(forRow: 1, inSection: 0)
        self.MenuTable.selectRowAtIndexPath(indexpath, animated: false, scrollPosition: UITableViewScrollPosition.None)
        if (self.current_vc == nil){
            self.performSegueWithIdentifier("MenuToMainSegue", sender: self)
        }
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    /**
     * Table view delegate functions
     */
    func tableView(tableView: UITableView, heightForRowAtIndexPath indexPath: NSIndexPath) -> CGFloat {
        var object = menu_vm.getCellSourceAtIndexPath(indexPath) as CellSource
        return object.heightForRowAtIndexPath()
    }
    
    func tableView(tableView: UITableView, didSelectRowAtIndexPath indexPath: NSIndexPath) {
        
        // get real index
        var realIndex = 0 as NSInteger
        
        // no action cases
        if (indexPath.row == 0 || indexPath.row == 3 || indexPath.row == 6){
            return
        }
        // share case
        else if (indexPath.row == 7){
            shareInfo(
                title: "PicJar",
                text: "Tell all your friends about PicJar! Now!",
                url: "http://picjar.com",
                img: UIImage(named: "shareicon")
            )
            return
        }
        // segue cases
        else if (indexPath.row == 1){
            realIndex = 0
        }
        else if (indexPath.row == 2){
            realIndex = 1
        }
        else if (indexPath.row == 4){
            realIndex = 2
        }
        else if (indexPath.row == 5){
            realIndex = 3
        }
        
        // initial re-setup
        self.menuOpened = false
        
        // close the menu and change the view controller
        var frame = self.current_vc.view.frame
        frame.origin.x = 0
        
        UIView.animateWithDuration(
            0.25,
            // perform animation
            animations: { () -> Void in
                self.current_vc.view.frame = frame
            },
            completion: { (Bool) -> Void in
                self.current_vc.view.removeFromSuperview()
                
                var object = self.menu_vm.getCellSourceAtIndexPath(indexPath) as! MenuRowCellSource
                self.performSegueWithIdentifier(object.getSegue(), sender: self)
            }
        )
    }
    
    /**
     * setters and getters
     */
    func setCurrentVC(vc: UIViewController) {
        self.current_vc = vc
    }
    
    func getCurrentVC() -> UIViewController{
        return self.current_vc
    }
    
    class func MenuVCOpenMenuNotification() -> String {
        return "OpenMenu"
    }
    
    /**
     * This function gets called when the observer sees that someone has posted
     * an "open menu" notification
     */
    func openMenu(){
        // see current state
        menuOpened = (menuOpened == true ? false : true)
        
        // prepare transition
        var dx = (menuOpened == true ? -260.0 : 260.0) as CGFloat
        var frame = self.current_vc.view.frame
        frame.origin.x -= dx
        
        // animate transition
        UIView.animateWithDuration(0.25, animations: { () -> Void in
            self.current_vc.view.frame = frame
        })
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
