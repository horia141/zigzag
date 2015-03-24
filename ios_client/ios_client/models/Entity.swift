/**
 * Entity.swift
 * Created by Liviu Coman on 3/24/15.
 * Copyright (c) 2015 LiviuComan. All rights reserved.
 */

import UIKit

/**
 * The base class for all model objects.
 * <p>
 * Contains an unique identifier for the object, up to its own name space.
 * <p>
 * Entities are immutable.
 */
class Entity: NSObject {
    /**
     * Declare the Entity's identifier (which is an explicitly initialized
     * optional, since an implicit optional would be too much hassle)
     */
    private var id: EntityId! = nil
    
    /**
     * Swift constructor, named init()
     *
     * @param: the Entity id object
     */
    init(id: EntityId) {
        self.id = id;
    }
    
    /**
     * Retrieve the id of the entity.
     *
     * @return The id of the entity.
     */
    func getId() -> EntityId {
        return self.id;
    }
}
