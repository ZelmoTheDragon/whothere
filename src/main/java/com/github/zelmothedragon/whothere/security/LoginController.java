package com.github.zelmothedragon.whothere.security;

import com.github.zelmothedragon.whothere.core.faces.Page;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 * Contrôleur de connexion.
 *
 * @author MOSELLE Maxime
 */
@Named
@RequestScoped
public class LoginController {

    /**
     * Constructeur par défaut. Requis pour le fonctionnement des technologies
     * de <i>Jakarta EE</i>.
     */
    public LoginController() {
        // Ne pas appeler explicitement.
    }

    /**
     * Connecter un utilisateur à partir des informations du formulaire.
     *
     * @param form Formulaire de connexion.
     */
    public String login(final Credentials form) {

        System.out.println("LOGIN:");
        System.out.println(form.getUsername());
        System.out.println(form.getPassword());
        return Page.HOME.redirect();
    }
}
