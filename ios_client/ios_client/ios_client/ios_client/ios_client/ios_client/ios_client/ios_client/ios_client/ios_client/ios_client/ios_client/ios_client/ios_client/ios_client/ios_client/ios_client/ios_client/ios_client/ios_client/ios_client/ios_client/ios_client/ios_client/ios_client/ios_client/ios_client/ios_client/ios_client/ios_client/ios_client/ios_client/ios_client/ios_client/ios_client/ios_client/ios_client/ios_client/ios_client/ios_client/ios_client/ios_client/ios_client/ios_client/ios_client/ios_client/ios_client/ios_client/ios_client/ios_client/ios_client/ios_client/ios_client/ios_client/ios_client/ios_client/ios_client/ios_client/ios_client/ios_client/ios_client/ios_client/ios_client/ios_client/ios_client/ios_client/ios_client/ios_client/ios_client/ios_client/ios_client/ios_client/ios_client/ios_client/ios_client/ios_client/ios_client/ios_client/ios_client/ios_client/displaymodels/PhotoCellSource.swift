/**
 * PhotoCellSource.swift
 * created by Liviu Coman on 3/24/15.
 * copyright (c) 2015 LiviuComan. All rights reserved.
 */

import UIKit

/**
 * Encompasses data for photo cell representation
 */
class PhotoCellSource: CellSource {
   /**
    * member vars
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
     * getters
     */
    func getSubCellSources() -> [CellSource] {
        return self.subcellsources
    }
}
