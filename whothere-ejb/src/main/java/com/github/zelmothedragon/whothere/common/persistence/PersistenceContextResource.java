package com.github.zelmothedragon.whothere.common.persistence;

import javax.enterprise.inject.Produces;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Producteur de ressource injectable. Cette classe est invoquée automatiquement
 * par <i>CDI</i>.
 *
 * @author MOSELLE Maxime
 */
@Singleton
public class PersistenceContextResource {

    /**
     * Gestionnaire d'entité.
     */
    @Produces
    @PersistenceContext
    private transient EntityManager em;

    /**
     * Constructeur par défaut. Requis pour le fonctionnement des technologies
     * de <i>Jakarta EE</i>.
     */
    public PersistenceContextResource() {
        // Ne pas appeler explicitement.
    }

}
