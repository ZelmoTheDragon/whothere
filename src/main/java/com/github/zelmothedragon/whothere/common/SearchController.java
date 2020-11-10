package com.github.zelmothedragon.whothere.common;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author MOSELLE Maxime
 */
@Named
@RequestScoped
public class SearchController {

    /**
     * Mot clef pour la recherche.
     */
    private String keyword;

    /**
     * Constructeur d'injection. Requis pour le fonctionnement des technologies
     * de Java EE.
     */
    public SearchController() {
        // Ne pas appeler explicitement.
    }

    /**
     * Rechercher un élément dans le site.
     */
    public void search() {
        System.out.println("SEARCHING: " + keyword);
    }

    // ------------------------------
    // Accesseurs & Mutateurs
    // ------------------------------
    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

}
