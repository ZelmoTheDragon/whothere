package com.github.zelmothedragon.whothere.core.faces;

import javax.faces.context.FacesContext;

/**
 * Enumération des pages disponibles.
 *
 * @author MOSELLE Maxime
 */
public enum Page {

    INDEX("/index"),
    LOGIN("/login"),
    HOME("/home"),
    DATATABLE_AGENT("/protected/agent"),
    FORM_AGENT("/protected/agent-form");

    /**
     * Extension réelle des pages.
     */
    private static final String extension = ".xhtml";

    /**
     * Paramètre de redirection.
     */
    private static final String REDIRECTION = "?faces-redirect=true";

    /**
     * Chemin d'une page.
     */
    private final String path;

    /**
     * Constructeur interne.
     *
     * @param path Chemin d'une page
     */
    Page(String path) {
        this.path = path;
    }

    /**
     * Obtenir l'adresse de redirection <i>JSF</i> pour une page.
     *
     * @return L'adresse de redirection
     */
    public String redirect() {
        return String.join(
                "",
                getContextPath(),
                path,
                extension,
                REDIRECTION
        );
    }

    /**
     * Obtenir le chemin racine de l'application.
     *
     * @return Le chemin racine
     */
    private static String getContextPath() {
        return FacesContext
                .getCurrentInstance()
                .getExternalContext()
                .getRequestContextPath();
    }

}
