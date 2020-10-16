package com.github.zelmothedragon.whothere.common.service;

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

    public Optional<? extends Identifiable<?>> find(final Class<? extends Identifiable<?>> entityClass, final Object id) {
        return JPA.get(entityClass, id);
    }

    public Collection<? extends Identifiable<?>> find(final Class<? extends Identifiable<?>> entityClass) {
        return JPA.get(entityClass);
    }

    public Identifiable<?> save(final Identifiable<?> entity) {
        return JPA.add(entity);
    }

    public void remove(final Identifiable<?> entity) {
        JPA.remove(entity);
    }

    public void remove(final Class<? extends Identifiable<?>> entityClass, Object id) {
        JPA.remove(id);
    }

}
