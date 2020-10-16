package com.github.zelmothedragon.whothere.common.controller;

import com.github.zelmothedragon.whothere.DynamicEntity;
import com.github.zelmothedragon.whothere.common.persistence.Identifiable;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public final class DynamicEntityMapper {

    private DynamicEntityMapper() {
        throw new UnsupportedOperationException("Instance not allowed");
    }

    public static Identifiable<?> mapToEntity(final String type, final String jsonEntity) {

        return DynamicEntity
                .fromTypeName(type)
                .map(DynamicEntity::getEntityClass)
                .map(e -> JsonbBuilder.create().fromJson(jsonEntity, e))
                .orElseThrow(() -> new WebApplicationException("Invalid entity", Response.Status.BAD_REQUEST));
    }

    public static Class<? extends Identifiable<?>> mapToEntityClass(final String type) {
        return DynamicEntity
                .fromTypeName(type)
                .map(DynamicEntity::getEntityClass)
                .orElseThrow(() -> new WebApplicationException("Invalid entity", Response.Status.BAD_REQUEST));
    }

    public static Object mapToEntityId(final String type, final String id) {
        return DynamicEntity
                .fromTypeName(type)
                .map(e -> e.convertAsIdentifier(id))
                .orElseThrow(() -> new WebApplicationException("Invalid entity", Response.Status.BAD_REQUEST));
    }

}
