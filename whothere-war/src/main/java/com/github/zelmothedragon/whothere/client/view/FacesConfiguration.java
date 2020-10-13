package com.github.zelmothedragon.whothere.client.view;

import javax.faces.annotation.FacesConfig;
import javax.inject.Singleton;

/**
 * Configuration de la technologie <i>JSF</i>.
 *
 * @author MOSELLE Maxime
 */
@Singleton
@FacesConfig(version = FacesConfig.Version.JSF_2_3)
public class FacesConfiguration {

    /**
     * Constructeur par défaut. Requis pour le fonctionnement des technologies
     * de <i>Jakarta EE</i>.
     */
    public FacesConfiguration() {
        // Ne pas appeler explicitement
    }

}
