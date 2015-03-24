/**
 * EntityId.swift
 * created by Liviu Coman on 3/24/15.
 * copyright (c) 2015 LiviuComan. All rights reserved.
 */

import UIKit

/**
 * An identifier for an entity.
 * <p>
 * This object is a stable identifier for a model entity, both inside the client application, as
 * well as through the whole system. The identifier simply consists of a string, but it is opaque
 * for all purposes. It is the job of the system or, more precisely, the server to generate them.
 * <p>
 * EntityIds are immutable, can be properly hashed, cloned and compared for equality.
 */
class EntityId: NSObject {
    private var id: String! = nil
    
    init(id: String){
        self.id = id
    }
    
    func getId() -> String {
        return self.id
    }
    
    func equals(var o: AnyObject) -> Bool{
        if (!(o is EntityId)){
            return false;
        }
        
        if (self == o as EntityId){
            return true;
        }
        
        if (self.id == (o as EntityId).getId()){
            return true;
        }
        
        return false;
    }
    
    /**
     * brief: this is kind of redundant, in swift all basic types that
     * can be dictionary keys are hashable
     */
    func hashCode() -> Int {
        return id.hash
    }
    
}
