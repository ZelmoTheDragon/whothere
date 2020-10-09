package com.github.zelmothedragon.whothere.common.persistence;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.Optional;
import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 * Classe mère pour les opérations de persistance générique.
 *
 * @param <E> Type d'entité persistante
 * @param <K> Type de la clef primaire
 * @author MOSELLE Maxime
 */
public abstract class AbstractDAO<E extends Identifiable<K>, K> implements Repository<E, K> {

    /**
     * Gestionnaire d'entité.
     */
    @Inject
    protected transient EntityManager em;

    /**
     * Constructeur d'injection.Requis pour le fonctionnement des technologies
     * de <i>Jakarta EE</i>.
     */
    protected AbstractDAO() {
        // Ne pas appeler explicitement.
    }

    @Override
    public boolean contains(final E entity) {
        return JPA.contains(entity);
    }

    @Override
    public boolean containsAll(final Collection<E> entities) {
        return JPA.containsAll(entities);
    }

    @Override
    public boolean isEmpty() {
        return JPA.isEmpty(getEntityClass());
    }

    @Override
    public long size() {
        return JPA.size(getEntityClass());
    }

    @Override
    public E add(final E entity) {
        return JPA.add(entity);
    }

    @Override
    public Collection<E> addAll(final Collection<E> entities) {
        return JPA.addAll(entities);
    }

    @Override
    public void remove(final E entity) {
        JPA.remove(entity);
    }

    @Override
    public void removeAll(final Collection<E> entities) {
        JPA.removeAll(entities);
    }

    @Override
    public Optional<E> get(final K id) {
        return JPA.get(getEntityClass(), id);
    }

    @Override
    public Collection<E> get() {
        return JPA.get(getEntityClass());
    }

    /**
     * Obtenir la classe de l'entité persistance.
     *
     * @return La classe de l'entité persistance
     */
    protected Class<E> getEntityClass() {
        var superClass = (ParameterizedType) getClass().getGenericSuperclass();
        var entityClass = (Class<E>) superClass.getActualTypeArguments()[0];
        return entityClass;
    }

}
