package com.github.zelmothedragon.whothere.common.session;

import com.github.zelmothedragon.whothere.common.persistence.Identifiable;
import com.github.zelmothedragon.whothere.common.persistence.Repository;
import java.util.Collection;
import java.util.Optional;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

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

    @TransactionAttribute(TransactionAttributeType.NEVER)
    @Override
    public Optional<E> find(final K id) {
        return repository.get(id);
    }

    @TransactionAttribute(TransactionAttributeType.NEVER)
    @Override
    public Collection<E> find() {
        return repository.get();
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
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

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    @Override
    public void remove(final E entity) {
        repository.remove(entity);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    @Override
    public void remove(K id) {
        repository.remove(id);
    }

}
