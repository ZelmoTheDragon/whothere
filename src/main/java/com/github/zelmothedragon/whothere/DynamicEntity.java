package com.github.zelmothedragon.whothere;


import com.github.zelmothedragon.whothere.common.Agent;
import com.github.zelmothedragon.whothere.common.Organization;
import com.github.zelmothedragon.whothere.core.persistence.Identifiable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

public enum DynamicEntity {

    AGENT("agent", Agent.class, UUID::fromString),
    ORGANIZATION("organization", Organization.class, UUID::fromString);

    private final String typeName;

    private final Class<? extends Identifiable<?>> entityClass;

    private final Function<String, Object> identifierConverter;

    private DynamicEntity(
            final String typeName,
            final Class<? extends Identifiable<?>> entityClass,
            final Function<String, Object> identifierConverter) {

        this.typeName = typeName;
        this.entityClass = entityClass;
        this.identifierConverter = identifierConverter;
    }

    public static Optional<DynamicEntity> fromTypeName(final String typeName) {
        return List
                .of(values())
                .stream()
                .filter(e -> Objects.equals(e.typeName, typeName))
                .findFirst();
    }

    public Object convertAsIdentifier(final String id) {
        return identifierConverter.apply(id);
    }

    public String getTypeName() {
        return typeName;
    }

    public Class<? extends Identifiable<?>> getEntityClass() {
        return entityClass;
    }

}
