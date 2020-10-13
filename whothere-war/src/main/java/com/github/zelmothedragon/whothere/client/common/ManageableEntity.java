package com.github.zelmothedragon.whothere.client.common;

import com.github.zelmothedragon.whothere.absence.State;
import com.github.zelmothedragon.whothere.common.persistence.Identifiable;

/**
 *
 * @author MOSELLE Maxime
 */
public enum ManageableEntity {

    STATE("state", State.class);

    private final String entityName;

    private final Class<? extends Identifiable<?>> entityClass;

    ManageableEntity(
            final String entityName,
            final Class<? extends Identifiable<?>> entityClass) {

        this.entityName = entityName;
        this.entityClass = entityClass;
    }

    public String getEntityName() {
        return entityName;
    }

    public Class<? extends Identifiable<?>> getEntityClass() {
        return entityClass;
    }

}
