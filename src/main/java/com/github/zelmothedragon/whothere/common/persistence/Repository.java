package com.github.zelmothedragon.whothere.common.persistence;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;
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
 * @see Repository
 */
@ApplicationScoped
public class Repository {

    /**
     * Gestionnaire d'entité.
     */
    @Inject
    EntityManager em;

    /**
     * Constructeur par défaut. Requis pour le fonctionnement des technologies
     * d'entreprise.
     */
    public Repository() {
        // Ne pas appeler explicitement.
    }

    /**
     * Obtenir une instance de travail de cette classe.
     *
     * @return Une instance de travail de cette classe
     */
    public static Repository of() {
        return CDI.current().select(Repository.class).get();
    }

    /**
     * Vérifier l'existence d'une entité.
     *
     * @param entity Entité persistante
     * @return La valeur {@code true} si l'entité existe, sinon la valeur
     * {@code false} est retournée
     */
    public boolean contains(final Object entity) {
        final boolean exists;
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
     * Vérifier l'existence d'une entité en fonction de son identifiant unique.
     *
     * @param entityClass Classe de l'entité persistante
     * @param id Identifiant unique
     * @return La valeur {@code true} si l'entité existe, sinon la valeur
     * {@code false} est retournée
     */
    public boolean contains(final Class<?> entityClass, final Object id) {
        boolean contains;
        var option = get(entityClass, id);
        if (option.isPresent()) {
            contains = true;
        } else {
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
     * Vérifier l'existence d'une collection d'entités.
     *
     * @param entities Collection d'entités persistantes
     * @return La valeur {@code true} si toute les entités de la collection
     * existe, sinon la valeur {@code false} est retournée
     */
    public boolean containsAll(final Collection<?> entities) {
        return entities.stream().allMatch(this::contains);
    }

    /**
     * Indiquer si aucune occurrence existe.
     *
     * @param entityClass Classe de l'entité persistante
     * @return La valeur {@code true} si aucune occurrence existe, sinon la
     * valeur {@code false} est retournée
     */
    public boolean isEmpty(final Class<?> entityClass) {
        return size(entityClass) == 0L;
    }

    /**
     * Obtenir le nombre d'occurrence enregistré.
     *
     * @param entityClass Classe de l'entité persistante
     * @return Le nombre d'occurrence
     */
    public long size(final Class<?> entityClass) {
        var cb = em.getCriteriaBuilder();
        var q = cb.createQuery(Long.class);
        var root = q.from(entityClass);
        q.select(cb.count(root));
        return em.createQuery(q).getSingleResult();
    }

    /**
     * Ajouter une nouvelle entité.Si l'entité existe déjà, elle sera mise à
     * jour.
     *
     * @param <E> Type de l'entité persistante
     * @param entity Entité persistante
     * @return L'entité enregistrée
     */
    public <E> E add(final E entity) {
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
     * Ajouter une nouvelle collection d'entités.Si les entités existent déjà,
     * il seront mise à jour.
     *
     * @param <E> Type de l'entité persistante
     * @param entities Collection d'entités persistantes
     * @return Une collection d'entités enregistrée
     */
    public <E> List<E> addAll(final Collection<E> entities) {
        return entities
                .stream()
                .map(this::add)
                .collect(Collectors.toList());
    }

    /**
     * Supprimer une entité.
     *
     * @param entity Entité persistante
     */
    public void remove(final Object entity) {
        var entityClass = entity.getClass();
        var id = getIdentifier(em, entity);
        var managedEntity = em.getReference(entityClass, id);
        em.remove(managedEntity);
    }

    /**
     * Supprimer une entité en fonction de son identifiant.
     *
     * @param entityClass Classe de l'entité persistante
     * @param id Identifiant unique.
     */
    public void remove(final Class<?> entityClass, Object id) {
        var option = get(entityClass, id);
        if (option.isPresent()) {
            remove(option.get());
        }
    }

    /**
     * Supprimer une collection d'entité.
     *
     * @param entities Collection d'entités persistantes
     */
    public void removeAll(final Collection<?> entities) {
        entities.forEach(this::remove);
    }

    /**
     * Rechercher une entité en fonction de son identifiant unique.
     *
     * @param <E> Type de l'entité persistante
     * @param entityClass Classe de l'entité persistante
     * @param id Clef primaire
     * @return Une option contenant ou non l'entité correspondante à
     * l'identifiant unique
     */
    public <E> Optional<E> get(final Class<E> entityClass, final Object id) {
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
     * Rechercher toutes les entités enregistrés.
     *
     * @param <E> Type de l'entité persistante
     * @param entityClass Classe de l'entité persistante
     * @return Une liste des entités persistantes
     */
    public <E> List<E> get(final Class<E> entityClass) {
        var cb = em.getCriteriaBuilder();
        var q = cb.createQuery(entityClass);
        q.from(entityClass);
        return em.createQuery(q).getResultList();
    }

    /**
     * Rechercher des entités enregistrées avec un critère de pagination.
     *
     * @param <E> Type de l'entité persistante
     * @param entityClass Classe de l'entité persistante
     * @param pagination Critère de pagination
     * @return Une liste des entités persistantes
     */
    public <E> List<E> filter(final Class<E> entityClass, final Pagination pagination) {

        var cb = em.getCriteriaBuilder();
        var q = cb.createQuery(entityClass);
        var root = q.from(entityClass);
        q.distinct(true);

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
            if (orderByAttributes.isEmpty() && pagination.isAscending()) {
                orders = List.of(cb.asc(root));
            } else if (orderByAttributes.isEmpty() && !pagination.isAscending()) {
                orders = List.of(cb.desc(root));
            } else if (pagination.isAscending()) {
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
     * Rechercher des entités enregistrées en fonction d'un mot clef.
     *
     * @param <E> Type de l'entité persistante
     * @param entityClass Classe de l'entité persistante
     * @param keyword Mot clef pour la recherche
     * @return Une liste des entités persistantes
     */
    public <E> List<E> filter(final Class<E> entityClass, final String keyword) {

        var pagination = new Pagination(
                keyword,
                0,
                -1,
                List.of(),
                true,
                true
        );

        return Repository.this.filter(entityClass, pagination);
    }

    /**
     * Récupérer l'identifiant unique d'une entité.
     *
     * @param em Gestionnaire d'entité
     * @param entity Entité persistante
     * @return L'identifiant unique de l'entité
     */
    private Object getIdentifier(final EntityManager em, final Object entity) {
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
    private String getIdentifierName(final EntityManager em, final Object entity) {
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
