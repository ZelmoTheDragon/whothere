package com.github.zelmothedragon.whothere.core.persistence;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Classe mère pour les opérations de persistances génériques.
 *
 * @param <E> Type d'entité persistante
 * @param <K> Type de la clef primaire
 * @author MOSELLE Maxime
 */
public abstract class AbstractDAO<E extends Identifiable<K>, K>
        implements Repository<E, K>, Serializable {

    /**
     * Numéro de série.
     */
    private static final long serialVersionUID = 1L;

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
    public boolean contains(final K id) {
        return JPA.contains(getEntityClass(), id);
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
    public void remove(K id) {
        JPA.remove(getEntityClass(), id);
    }

    @Override
    public void removeAll(final Collection<E> entities) {
        JPA.removeAll(entities);
    }

    @Override
    public Optional<E> find(final K id) {
        return JPA.get(getEntityClass(), id);
    }

    @Override
    public List<E> get() {
        return JPA.get(getEntityClass());
    }

    @Override
    public List<E> filter(final Pagination pagination) {
        return JPA.get(getEntityClass(), pagination);
    }

    @Override
    public List<E> filter(String keyword) {
        return JPA.get(getEntityClass(), keyword);
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
