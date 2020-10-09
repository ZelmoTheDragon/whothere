package com.github.zelmothedragon.whothere;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;

/**
 * Utilitaire pour la construction d'archives déployables pour les tests.
 *
 * @author MOSELLE Maxime
 */
public final class Archive {

    /**
     * Constructeur interne. Pas d'instanciation.
     */
    private Archive() {
        throw new UnsupportedOperationException("Instance not allowed");
    }

    /**
     * Construire une archive à déployer sur le serveur d'application embarqué.
     * Cette archive comporte les classes et la configuration necessaire pour
     * l'éxecution des tests.
     *
     * @return Une archive à déployer avec <i>Arquillian</i>
     */
    public static WebArchive createDeployment() {
        return ShrinkWrap
                .create(WebArchive.class)
                .addAsWebInfResource("beans.xml")
                .addAsWebInfResource("web.xml")
                .addAsWebInfResource("payara-resources.xml")
                .addAsResource("persistence.xml", "META-INF/persistence.xml")
                .addPackages(true, "com.github.zelmothedragon.whothere");
    }
}
