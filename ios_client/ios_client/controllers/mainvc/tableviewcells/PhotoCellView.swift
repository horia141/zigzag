/**
 * PhotoCellView.swift
 * created by Liviu Coman on 3/24/15.
 * copyright (c) 2015 LiviuComan. All rights reserved.
 */
import UIKit

class PhotoCellView: UITableViewCell, UITableViewDelegate {

    @IBOutlet weak var ContentTable: UITableView!
    private var photo_cell_vm: TableCellVM! = nil
    
    override func awakeFromNib() {
        super.awakeFromNib()
        
        // register nibs and such
        self.ContentTable.registerNib(UINib(nibName: "TileCellView", bundle: nil), forCellReuseIdentifier: "TileCellViewId")
        self.ContentTable.registerNib(UINib(nibName: "SubtitleCellView", bundle: nil), forCellReuseIdentifier: "SubtitleCellViewId")
        self.ContentTable.registerNib(UINib(nibName: "DescriptionCellView", bundle: nil), forCellReuseIdentifier: "DescriptionCellViewId")
        
        // Initialization code
        self.ContentTable.layer.shadowColor = UIColor.blackColor().CGColor
        self.ContentTable.layer.shadowOpacity = 0.2
        self.ContentTable.layer.shadowOffset = CGSizeZero
        self.ContentTable.layer.masksToBounds = false
        self.ContentTable.layer.shadowRadius = 2
    }

    func initWithSubCellSources(subCellSources: [CellSource]){
        println("init video cell here with subcellsources")
        photo_cell_vm = TableCellVM(subCellSources: subCellSources)
        ContentTable.dataSource = photo_cell_vm
        self.ContentTable.reloadData()
    }
    
    override func setSelected(selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)
        // Configure the view for the selected state
    }
    
    /**
     * Table view delegate
     */
    func tableView(tableView: UITableView, heightForRowAtIndexPath indexPath: NSIndexPath) -> CGFloat {
        var object = photo_cell_vm.getSubCellSourceAtIndexPath(indexPath) as CellSource
        return object.heightForRowAtIndexPath()
    }
    
    func tableView(tableView: UITableView, willDisplayCell cell: UITableViewCell, forRowAtIndexPath indexPath: NSIndexPath) {
        cell.layer.masksToBounds = true
        cell.clipsToBounds = true
        cell.backgroundColor = UIColor.clearColor()
    }
}
