package com.github.zelmothedragon.whothere;

import com.github.zelmothedragon.whothere.agent.Agent;
import com.github.zelmothedragon.whothere.agent.Organization;
import com.github.zelmothedragon.whothere.core.persistence.Identifiable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Supplier;

public enum DynamicEntity {

    AGENT("agent", Agent.class, UUID::fromString, Agent::new),
    ORGANIZATION("organization", Organization.class, UUID::fromString, Agent::new);

    private final String typeName;

    private final Class<? extends Identifiable<?>> entityClass;

    private final Function<String, Object> identifierConverter;

    private final Supplier<Identifiable<?>> constructor;
    
    DynamicEntity(
            final String typeName,
            final Class<? extends Identifiable<?>> entityClass,
            final Function<String, Object> identifierConverter,
            final Supplier<Identifiable<?>> constructor) {

        this.typeName = typeName;
        this.entityClass = entityClass;
        this.identifierConverter = identifierConverter;
        this.constructor = constructor;
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

    public Identifiable<?> newInstance() {
        return constructor.get();
    }

    public String getTypeName() {
        return typeName;
    }

    public Class<? extends Identifiable<?>> getEntityClass() {
        return entityClass;
    }

}
