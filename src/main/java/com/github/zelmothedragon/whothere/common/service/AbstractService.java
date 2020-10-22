package com.github.zelmothedragon.whothere.common.service;

import com.github.zelmothedragon.whothere.common.persistence.Identifiable;
import com.github.zelmothedragon.whothere.common.persistence.Pagination;
import com.github.zelmothedragon.whothere.common.persistence.Repository;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.Optional;
import javax.enterprise.inject.spi.CDI;

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
    public Collection<E> find() {
        return null;
    }
    
    @Override
    public Collection<E> find(final Pagination pagination) {
        return service.find(getEntityClass(), pagination);
    }
    
    @Override
    public E save(final E entity) {
        return service.save(entity);
    }
    
    @Override
    public boolean exists(E entity) {
        return false;
    }
    
    @Override
    public boolean exists(K id) {
        return false;
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
