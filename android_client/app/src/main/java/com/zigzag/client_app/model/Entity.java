package com.zigzag.client_app.model;

/**
 * The base class for all model objects.
 * <p>
 * Contains an unique identifier for the object, up to its own name space.
 * <p>
 * Entities are immutable.
 */
public class Entity {
    /**
     * The identifier for the entity.
     */
    private final EntityId id;

    /**
     * Construct an entity with a given id.
     *
     * @param id The id to use for the entity.
     */
    protected Entity(EntityId id) {
        this.id = id;
    }

    /**
     * Retrieve the id of the entity.
     *
     * @return The id of the entity.
     */
    public EntityId getId() {
        return id;
    }
}
