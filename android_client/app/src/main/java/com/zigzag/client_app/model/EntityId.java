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
    /**
     * The string representation for the entity identifier.
     */
    private final String id;

    /**
     * Construct an entity ID from a given string.
     *
     * @param id The string representation for the entity identifier.
     */
    public EntityId(String id) {
        this.id = id;
    }

    /**
     * Compute a 32 bit hash code for the entity identifier.
     *
     * @return The 32 bit hash code.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    /**
     * Test whether the current object is equal to another object.
     * <p>
     * Equality means that both objects store the same string representation for the identifier.
     *
     * @return True if the other object is equal according to the above definition, otherwise False.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (!(obj instanceof EntityId)) {
            return false;
        }

        EntityId other = (EntityId) obj;

        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }

        return true;
    }

    /**
     * Produce a new EntityId object which is equal to the current one.
     *
     * @return The new EntityId object.
     */
    @Override
    public EntityId clone() {
        return new EntityId(new String(id));
    }

    /**
     * Retrieve the string representation of the entity identifier.
     *
     * @return The string representation of the entity identifier.
     */
    public String getId() {
        return id;
    }
}
