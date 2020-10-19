package com.github.zelmothedragon.whothere.common.persistence;

import java.io.Serializable;

/**
 * Modèle de données pour la recherche filtrée et paginée.
 *
 * @author MOSELLE Maxime
 */
public class Pagination implements Serializable {

    /**
     * Numéro de série.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Nombres d'occurence par page.
     */
    private static final int PAGINATION_LIMIT = 500;

    /**
     * Index dans la pagination.
     */
    public static final int DEFAULT_INDEX = 1;

    /**
     * Classe de l'entité
     */
    private final Class<?> entityClass;

    /**
     * Mot-clef pour la recherche.
     */
    private final String keyword;

    /**
     * Pagination.
     */
    private final int pageSize;

    /**
     * Index.
     */
    private final int pageNumber;

    /**
     * Constuire un modèle de données pour la pagniation.
     *
     * @param entityClass Classe de l'entité
     * @param keyword Mot-clef pour la recherche
     * @param pageNumber Numéro de page
     * @param pageSize Nombre d'élément par page
     */
    public Pagination(
            final Class<?> entityClass,
            final String keyword,
            final int pageNumber,
            final int pageSize) {

        this.entityClass = entityClass;
        this.keyword = keyword;
        this.pageNumber = Math.max(pageNumber, DEFAULT_INDEX);
        this.pageSize = Math.max(pageSize, PAGINATION_LIMIT);
    }

    // ------------------------------
    // Accesseurs
    // ------------------------------
    public Class<?> getEntityClass() {
        return entityClass;
    }

    public String getKeyword() {
        return keyword;
    }

    public int getIndex() {
        return (pageNumber - 1) * pageSize;
    }

    public int getPageSize() {
        return pageSize;
    }

}
