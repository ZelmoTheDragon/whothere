package com.github.zelmothedragon.whothere.core.service;

import com.github.zelmothedragon.whothere.common.CommonService;
import com.github.zelmothedragon.whothere.core.persistence.Identifiable;
import com.github.zelmothedragon.whothere.core.persistence.Pagination;
import com.github.zelmothedragon.whothere.core.persistence.Repository;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;

/**
 * Classe mère pour les opérations métiers génériques.
 *
 * @param <D> Type d'entrepôt de données
 * @param <E> Type d'entité métier
 * @param <K> Type d'identifiant unique
 * @author MOSELLE Maxime
 */
public abstract class AbstractService<E extends Identifiable<K>, K, D extends Repository<E, K>>
        implements Service<E, K> {

    private final CommonService service;

    protected AbstractService(final CommonService service) {
        this.service = service;

    }

    @Override
    public Optional<E> find(final K id) {
        return service.find(getEntityClass(), id);
    }

    @Override
    public List<E> find() {
        return service.find(getEntityClass());
    }

    @Override
    public List<E> filter(final Pagination pagination) {
        return service.filter(getEntityClass(), pagination);
    }

    @Override
    public List<E> filter(String keyword) {
        return service.filter(getEntityClass(), keyword);
    }

    @Override
    public E save(final E entity) {
        return service.save(entity);
    }

    @Override
    public boolean exists(E entity) {
        return service.exists(entity);
    }

    @Override
    public boolean exists(K id) {
        return service.exists(getEntityClass(), id);
    }

    @Override
    public void remove(final E entity) {
        service.remove(entity);
    }

    @Override
    public void remove(K id) {
        service.remove(getEntityClass(), id);
    }

    /**
     * Obtenir la classe de l'entité métier.
     *
     * @return La classe de l'entité métier
     */
    protected Class<E> getEntityClass() {
        var superClass = (ParameterizedType) getClass().getGenericSuperclass();
        var entityClass = (Class<E>) superClass.getActualTypeArguments()[0];
        return entityClass;
    }
}
