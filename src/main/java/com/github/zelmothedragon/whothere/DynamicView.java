package com.github.zelmothedragon.whothere;

import com.github.zelmothedragon.whothere.core.faces.Page;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 *
 * @author MOSELLE Maxime
 */
public enum DynamicView {

    AGENT(DynamicEntity.AGENT, Page.DATATABLE_AGENT, Page.FORM_AGENT);

    private final DynamicEntity entityType;

    private final Page datatable;

    private final Page form;

    DynamicView(
            final DynamicEntity entityType,
            final Page datatable,
            final Page form) {

        this.entityType = entityType;
        this.datatable = datatable;
        this.form = form;
    }

    public static Optional<DynamicView> fromEntityType(final DynamicEntity entityType) {

        return List
                .of(values())
                .stream()
                .filter(e -> Objects.equals(e.entityType, entityType))
                .findFirst();
    }

    public DynamicEntity getEntityType() {
        return entityType;
    }

    public Page getDatatable() {
        return datatable;
    }

    public Page getForm() {
        return form;
    }

}
