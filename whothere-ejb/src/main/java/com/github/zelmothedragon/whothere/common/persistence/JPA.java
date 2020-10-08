package com.github.zelmothedragon.whothere.common.persistence;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;

/**
 * Utilitaire <i>JPA</i> pour les opérations de base sur les entités
 * persistantes.
 *
 * @author MOSELLE Maxime
 */
public final class JPA {

    /**
     * Constructeur interne. Pas d'instanciation.
     */
    private JPA() {
        throw new UnsupportedOperationException("Instance not allowed");
    }

    static boolean contains(final EntityManager em, final Object entity) {
        boolean exists;
        var entityClass = entity.getClass();
        if (em.contains(entity)) {
            exists = true;
        } else {
            var cb = em.getCriteriaBuilder();
            var query = cb.createQuery(Long.class);
            var root = query.from(entityClass);
            query.select(cb.count(root));
            var id = getIdentifier(em, entity);
            var idName = getIdentifierName(em, entity);
            var predicate = cb.equal(root.get(idName), id);
            query.where(predicate);
            var result = em.createQuery(query).getSingleResult();
            exists = result == 1L;
        }
        return exists;
    }

    static boolean containsAll(final EntityManager em, final Collection<? extends Identifiable<?>> entities) {
        return entities.stream().allMatch(e -> contains(em, e));
    }

    static boolean isEmpty(final EntityManager em, final Class<?> entityClass) {
        return size(em, entityClass) == 0L;
    }

    static long size(final EntityManager em, final Class<?> entityClass) {
        var cb = em.getCriteriaBuilder();
        var q = cb.createQuery(Long.class);
        var root = q.from(entityClass);
        q.select(cb.count(root));
        return em.createQuery(q).getSingleResult();
    }

    static <E> E add(final EntityManager em, final E entity) {
        E managedEntity;
        if (contains(em, entity)) {
            managedEntity = em.merge(entity);
        } else {
            em.persist(entity);
            managedEntity = entity;
        }
        return managedEntity;
    }

    static <E> List<E> addAll(final EntityManager em, final Collection<E> entities) {
        return entities
                .stream()
                .map(e -> add(em, e))
                .collect(Collectors.toList());
    }

    static void remove(final EntityManager em, final Object entity) {
        var entityClass = entity.getClass();
        var id = getIdentifier(em, entity);
        var managedEntity = em.getReference(entityClass, id);
        em.remove(managedEntity);
    }

    static void removeAll(final EntityManager em, final Collection<?> entities) {
        entities.forEach(e -> remove(em, e));
    }

    static <E> Optional<E> get(final EntityManager em, final Class<E> entityClass, final Object id) {
        Optional<E> option;
        if (Objects.isNull(id)) {
            option = Optional.empty();
        } else {
            var entity = em.find(entityClass, id);
            option = Optional.ofNullable(entity);
        }
        return option;
    }

    static <E> List<E> get(final EntityManager em, final Class<E> entityClass) {
        var cb = em.getCriteriaBuilder();
        var q = cb.createQuery(entityClass);
        q.from(entityClass);
        return em.createQuery(q).getResultList();
    }

    private static Object getIdentifier(final EntityManager em, final Object entity) {
        return em
                .getEntityManagerFactory()
                .getPersistenceUnitUtil()
                .getIdentifier(entity);
    }

    private static String getIdentifierName(final EntityManager em, final Object entity) {
        var entityClass = entity.getClass();
        var id = getIdentifier(em, entity);
        var idClass = id.getClass();
        return em
                .getMetamodel()
                .entity(entityClass)
                .getId(idClass)
                .getName();
    }
}
