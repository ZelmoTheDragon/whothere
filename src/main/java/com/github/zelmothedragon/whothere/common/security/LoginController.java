package com.github.zelmothedragon.whothere.common.security;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author MOSELLE Maxime
 */
@Named
@RequestScoped
public class LoginController {

    public LoginController() {
    }

    public void login(Credentials credentials) {

        System.out.println("LOGIN:");
        System.out.println(credentials.getUsername());
        System.out.println(credentials.getPassword());
    }
}
