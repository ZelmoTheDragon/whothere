package com.github.zelmothedragon.whothere.common.service;

import com.github.zelmothedragon.whothere.common.persistence.Pagination;
import com.github.zelmothedragon.whothere.common.persistence.Identifiable;
import com.github.zelmothedragon.whothere.common.persistence.JPA;
import java.util.Collection;
import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

/**
 * Implémentation du service commun avec <i>CDI</i>.
 *
 * @author MOSELLE Maxime
 */
@ApplicationScoped
@Transactional
public class CommonService {

    /**
     * Constructeur par défaut. Requis pour le fonctionnement des technologies
     * de <i>Jakarta EE</i>.
     */
    public CommonService() {
        // Ne pas appeler explicitement.
    }

    public <E extends Identifiable<?>> Optional<E> find(
            final Class<E> entityClass,
            final Object id) {

        return JPA.get(entityClass, id);
    }

    public <E extends Identifiable<?>> Collection<E> find(
            final Class<E> entityClass,
            final Pagination pagination) {

        return JPA.get(entityClass, pagination);
    }

    public boolean exists(final Identifiable<?> entity) {
        return JPA.contains(entity);
    }

    public boolean exists(
            final Class<? extends Identifiable<?>> entityClass,
            final Object id) {

        return JPA.contains(entityClass, id);
    }

    public <E extends Identifiable<?>> E save(final E entity) {
        entity.checkId();
        return JPA.add(entity);
    }

    public void remove(final Identifiable<?> entity) {
        JPA.remove(entity);
    }

    public void remove(
            final Class<? extends Identifiable<?>> entityClass,
            final Object id) {

        var entity = JPA.get(entityClass, id);
        entity.ifPresent(e -> JPA.remove(e));
    }

}
