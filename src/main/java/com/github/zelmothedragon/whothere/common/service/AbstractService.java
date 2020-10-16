package com.github.zelmothedragon.whothere.common.service;

import com.github.zelmothedragon.whothere.common.persistence.Identifiable;
import com.github.zelmothedragon.whothere.common.persistence.Repository;
import java.util.Collection;
import java.util.Optional;


/**
 * Classe mère pour les opérations métiers génériques.
 *
 * @param <D> Type d'entrepôt de données
 * @param <E> Type d'entité métier
 * @param <K> Type d'identifiant unique
 * @author MOSELLE Maxime
 */
public abstract class AbstractService<D extends Repository<E, K>, E extends Identifiable<K>, K>
        implements Service<E, K> {

    protected final D repository;

    protected AbstractService(final D repository) {
        this.repository = repository;
    }

    @Override
    public Optional<E> find(final K id) {
        return repository.get(id);
    }

    @Override
    public Collection<E> find() {
        return repository.get();
    }

    @Override
    public E save(final E entity) {
        return repository.add(entity);
    }

    @Override
    public boolean exists(E entity) {
        return repository.contains(entity);
    }

    @Override
    public boolean exists(K id) {
        return repository.contains(id);
    }

    @Override
    public void remove(final E entity) {
        repository.remove(entity);
    }

    @Override
    public void remove(K id) {
        repository.remove(id);
    }

}
