package com.github.zelmothedragon.whothere.common.faces;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.annotation.FacesConfig;

/**
 * Classe de configuration de <i>JSF</i>.
 *
 * @author MOSELLE Maxime
 */
@ApplicationScoped
@FacesConfig(version = FacesConfig.Version.JSF_2_3)
public class FacesConfiguration {

    public static final String CONFIG_PRIMEFACES_THEME = "primefaces.THEME";

    /**
     * Constructeur par défaut. Requis pour le fonctionnement des technologies
     * d'entreprise.
     */
    public FacesConfiguration() {
        // Ne pas appeler explicitement.
    }

}
