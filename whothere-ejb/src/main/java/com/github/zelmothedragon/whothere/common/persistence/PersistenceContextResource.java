package com.github.zelmothedragon.whothere.common.persistence;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

/**
 * Producteur de ressource injectable. Cette classe est invoquée automatiquement
 * par <i>CDI</i>.
 *
 * @author MOSELLE Maxime
 */
@ApplicationScoped
public class PersistenceContextResource {

    /**
     * Constructeur par défaut. Requis pour le fonctionnement des technologies
     * de <i>Jakarta EE</i>.
     */
    public PersistenceContextResource() {
        // Ne pas appeler explicitement.
    }

    /**
     * Produire un point d'injection pour le gestionnaire d'entité.
     *
     * @return Une instance injectable
     */
    @Produces
    public EntityManager createNewEntityManager() {
        // Ne pas appeler explicitement.
        return Persistence
                .createEntityManagerFactory("whothere-pu")
                .createEntityManager();
    }

    /**
     * Fermer le gestionnaire d'entité lorsque son instance est en fin vie.
     *
     * @param em Une instance injectée
     */
    public void disposeEntityManager(@Disposes final EntityManager em) {
        // Ne pas appeler explicitement.
        em.clear();
        em.close();
    }
}
