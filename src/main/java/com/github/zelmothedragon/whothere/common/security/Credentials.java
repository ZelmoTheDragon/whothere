package com.github.zelmothedragon.whothere.common.security;

import javax.enterprise.inject.Model;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 *
 * @author MOSELLE Maxime
 */
@Model
public class Credentials {

    @NotBlank
    @Size(min = 2, max = 255)
    private String username;

    @NotBlank
    @Size(min = 2, max = 255)
    private String password;

    public Credentials() {
    }
    
    // ------------------------------
    // Accesseurs & Mutateurs
    // ------------------------------

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
