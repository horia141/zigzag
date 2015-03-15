package com.zigzag.client_app.model;

/**
 * An identifier for an entity.
 * <p>
 * This object is a stable identifier for a model entity, both inside the client application, as
 * well as through the whole system. The identifier simply consists of a string, but it is opaque
 * for all purposes. It is the job of the system or, more precisely, the server to generate them.
 * <p>
 * EntityIds are immutable, can be properly hashed, cloned and compared for equality.
 */
public class EntityId {
    private final String id;

    public EntityId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof EntityId)) {
            return false;
        }

        EntityId entityId = (EntityId) o;

        if (!id.equals(entityId.id)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
