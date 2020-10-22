package com.github.zelmothedragon.whothere.common.persistence;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.enterprise.inject.spi.CDI;
import javax.persistence.EntityManager;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.SingularAttribute;

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

    /**
     * @see Repository#contains(Object)
     * @param entity Entité persistante
     * @return La valeur {@code true} si l'entité existe, sinon la valeur
     * {@code false} est retournée
     */
    public static boolean contains(final Identifiable<?> entity) {
        final boolean exists;
        var em = CDI.current().select(EntityManager.class).get();
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

    /**
     * @see Repository#contains(Object)
     * @param entityClass Classe de l'entité persistante
     * @param id Identifiant unique
     * @return La valeur {@code true} si l'entité existe, sinon la valeur
     * {@code false} est retournée
     */
    public static boolean contains(final Class<? extends Identifiable<?>> entityClass, final Object id) {
        boolean contains;
        var option = get(entityClass, id);
        if (option.isPresent()) {
            contains = true;
        } else {
            var em = CDI.current().select(EntityManager.class).get();
            var cb = em.getCriteriaBuilder();
            var q = cb.createQuery(Long.class);
            var root = q.from(entityClass);
            q.select(cb.count(root));

            em
                    .getMetamodel()
                    .entity(entityClass)
                    .getSingularAttributes()
                    .stream()
                    .findFirst()
                    .map(SingularAttribute::getName)
                    .map(e -> cb.equal(root.get(e), id))
                    .ifPresent(e -> q.where(e));

            contains = em.createQuery(q).getSingleResult() == 1L;
        }
        return contains;
    }

    /**
     * @see Repository#containsAll(Collection)
     * @param entities Collection d'entités persistantes
     * @return La valeur {@code true} si toute les entités de la collection
     * existe, sinon la valeur {@code false} est retournée
     */
    public static boolean containsAll(final Collection<? extends Identifiable<?>> entities) {
        return entities.stream().allMatch(JPA::contains);
    }

    /**
     * @see Repository#isEmpty()
     * @param entityClass Classe de l'entité persistante
     * @return La valeur {@code true} si aucune occurrence existe, sinon la
     * valeur {@code false} est retournée
     */
    public static boolean isEmpty(final Class<? extends Identifiable<?>> entityClass) {
        return size(entityClass) == 0L;
    }

    /**
     * @see Repository#size()
     * @param entityClass Classe de l'entité persistante
     * @return Le nombre d'occurrence
     */
    public static long size(final Class<? extends Identifiable<?>> entityClass) {
        var em = CDI.current().select(EntityManager.class).get();
        var cb = em.getCriteriaBuilder();
        var q = cb.createQuery(Long.class);
        var root = q.from(entityClass);
        q.select(cb.count(root));
        return em.createQuery(q).getSingleResult();
    }

    /**
     * @see Repository#add(Identifiable)
     * @param <E> Type d'entité persistante
     * @param entity Entité persistante
     * @return L'entité persisté
     */
    public static <E extends Identifiable<?>> E add(final E entity) {
        var em = CDI.current().select(EntityManager.class).get();
        E managedEntity;
        if (contains(entity)) {
            managedEntity = em.merge(entity);
        } else {
            em.persist(entity);
            managedEntity = entity;
        }
        return managedEntity;
    }

    /**
     * @see Repository#addAll(Collection)
     * @param <E> Type d'entité persistante
     * @param entities Collection d'entités persistantes
     * @return Les entités persistées
     */
    public static <E extends Identifiable<?>> List<E> addAll(final Collection<E> entities) {
        return entities
                .stream()
                .map(JPA::add)
                .collect(Collectors.toList());
    }

    /**
     * @see Repository#remove(Identifiable)
     * @param entity Entité persistante
     */
    public static void remove(final Identifiable<?> entity) {
        var em = CDI.current().select(EntityManager.class).get();
        var entityClass = entity.getClass();
        var id = getIdentifier(em, entity);
        var managedEntity = em.getReference(entityClass, id);
        em.remove(managedEntity);
    }

    /**
     * @see Repository#remove(Object)
     * @param entityClass Classe de l'entité persistante
     * @param id Identifiant unique
     */
    public static void remove(final Class<? extends Identifiable<?>> entityClass, Object id) {
        var option = get(entityClass, id);
        if (option.isPresent()) {
            remove(option.get());
        }
    }

    /**
     * @see Repository#removeAll(Collection)
     * @param entities Collection d'entités persistantes
     */
    public static void removeAll(final Collection<? extends Identifiable<?>> entities) {
        entities.forEach(JPA::remove);
    }

    /**
     * @see Repository#get(Object)
     * @param <E> Type d'entité persistante
     * @param entityClass Classe de l'entité persistante
     * @param id Clef primaire
     * @return Une option contenant ou non l'entité correspondante à
     * l'identifiant unique
     */
    public static <E extends Identifiable<?>> Optional<E> get(final Class<E> entityClass, final Object id) {
        var em = CDI.current().select(EntityManager.class).get();
        Optional<E> option;
        if (Objects.isNull(id)) {
            option = Optional.empty();
        } else {
            var entity = em.find(entityClass, id);
            option = Optional.ofNullable(entity);
        }
        return option;
    }

    /**
     * @see Repository#get()
     * @param <E> Type d'entité persistante
     * @param entityClass Classe de l'entité persistante
     * @return Une liste d'entités persistantes
     */
    public static <E extends Identifiable<?>> List<E> get(final Class<E> entityClass) {
        var em = CDI.current().select(EntityManager.class).get();
        var cb = em.getCriteriaBuilder();
        var q = cb.createQuery(entityClass);
        q.from(entityClass);
        return em.createQuery(q).getResultList();
    }

    /**
     * @see Repository#get(Pagination)
     * @param <E> Type d'entité persistante
     * @param entityClass Classe de l'entité persistante
     * @param pagination Critère de filtrage pour la pagination
     * @return Une liste d'entités persistantes
     */
    public static <E extends Identifiable<?>> Collection<E> get(
            final Class<E> entityClass,
            final Pagination pagination) {

        var em = CDI.current().select(EntityManager.class).get();
        var cb = em.getCriteriaBuilder();
        var q = cb.createQuery(entityClass);
        var root = q.from(entityClass);

        if (Objects.nonNull(pagination.getKeyword())) {
            String search = String.format("%%%s%%", pagination.getKeyword().trim().toLowerCase());

            var restrictions = em
                    .getMetamodel()
                    .entity(entityClass)
                    .getAttributes()
                    .stream()
                    .filter(a -> Objects.equals(a.getJavaType(), String.class))
                    .map(a -> cb.like(cb.lower(root.get(a.getName())), search))
                    .collect(Collectors.toList());

            q.where(cb.or(restrictions.toArray(new Predicate[restrictions.size()])));
        }

        if (pagination.isOrdered()) {
            var orderByAttributes = em
                    .getMetamodel()
                    .entity(entityClass)
                    .getAttributes()
                    .stream()
                    .map(Attribute::getName)
                    .filter(pagination.getOrderBy()::contains)
                    .collect(Collectors.toList());

            List<Order> orders;
            if (pagination.isAscending()) {
                orders = orderByAttributes
                        .stream()
                        .map(a -> cb.asc(root.get(a)))
                        .collect(Collectors.toList());
            } else {
                orders = orderByAttributes
                        .stream()
                        .map(a -> cb.desc(root.get(a)))
                        .collect(Collectors.toList());
            }
            q.orderBy(orders);
        }

        List<E> result;
        if (pagination.isNoLimit()) {
            result = em
                    .createQuery(q)
                    .getResultList();
        } else {
            result = em
                    .createQuery(q)
                    .setFirstResult(pagination.getIndex())
                    .setMaxResults(pagination.getPageSize())
                    .getResultList();
        }
        return result;
    }

    /**
     * Récupérer l'identifiant unique d'une entité.
     *
     * @param em Gestionnaire d'entité
     * @param entity Entité persistante
     * @return L'identifiant unique de l'entité
     */
    private static Object getIdentifier(final EntityManager em, final Object entity) {
        return em
                .getEntityManagerFactory()
                .getPersistenceUnitUtil()
                .getIdentifier(entity);
    }

    /**
     * Récupérer le nom de l'identifiant unique d'une entité.
     *
     * @param em Gestionnaire d'entité
     * @param entity Entité persistante
     * @return Le nom de l'identifiant unique
     */
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
