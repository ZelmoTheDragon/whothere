package com.github.zelmothedragon.whothere.common;

import com.github.zelmothedragon.whothere.core.persistence.Pagination;
import com.github.zelmothedragon.whothere.core.persistence.Identifiable;
import com.github.zelmothedragon.whothere.core.persistence.JPA;
import java.util.List;
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

    public <E extends Identifiable<?>> List<E> filter(
            final Class<E> entityClass,
            final Pagination pagination) {

        return JPA.get(entityClass, pagination);
    }

    public <E extends Identifiable<?>> List<E> filter(
            final Class<E> entityClass,
            final String keyword) {

        return JPA.get(entityClass, keyword);
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
