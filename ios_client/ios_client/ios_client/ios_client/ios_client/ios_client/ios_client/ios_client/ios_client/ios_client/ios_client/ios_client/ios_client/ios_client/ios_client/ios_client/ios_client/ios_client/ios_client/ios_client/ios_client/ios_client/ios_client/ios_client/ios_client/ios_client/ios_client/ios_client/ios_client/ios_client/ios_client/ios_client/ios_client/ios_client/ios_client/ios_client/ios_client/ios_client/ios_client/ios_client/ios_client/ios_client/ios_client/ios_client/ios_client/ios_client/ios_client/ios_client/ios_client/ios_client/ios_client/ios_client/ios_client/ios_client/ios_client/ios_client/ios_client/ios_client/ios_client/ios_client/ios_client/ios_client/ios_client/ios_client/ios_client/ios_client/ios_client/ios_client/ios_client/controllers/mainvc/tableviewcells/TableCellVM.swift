/**
 * VideoCellVM.swift
 * created by Liviu Coman on 3/24/15.
 * copyright (c) 2015 LiviuComan. All rights reserved.
 */

import UIKit

/**
 * Class that acts as view manager and data source for a VideoCellView object
 */
class TableCellVM: NSObject, UITableViewDataSource {
    /**
     * private vars
     */
    private var subcellsources: [CellSource] = []
    
    /**
     * custom init
     */
    init(subCellSources: [CellSource]) {
        super.init()
        self.subcellsources = subCellSources
    }
    
    /**
     * custom functions
     */
    func getSubCellSourceAtIndexPath(indexPath: NSIndexPath) -> CellSource{
        return subcellsources[indexPath.row]
    }
    
    func getSubCellSourcesCount() -> NSInteger {
        return subcellsources.count
    }
    
    /**
     * Table view data source delegate methods
     */
    func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        println("This video type cell has \(subcellsources.count) elements")
        return subcellsources.count
    }
    
    func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        var object = subcellsources[indexPath.row] as CellSource
        return object.tableView(tableView, representationAsCellForIndexPath: indexPath)
    }
}
