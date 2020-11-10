package com.github.zelmothedragon.whothere.core.faces;

import com.github.zelmothedragon.whothere.DynamicEntity;
import com.github.zelmothedragon.whothere.core.service.CommonService;
import com.github.zelmothedragon.whothere.core.persistence.Identifiable;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Contrôleur générique pour la recherche de données par mot clef.
 *
 * @author MOSELLE Maxime
 */
@Named
@ViewScoped
public class SearchController implements Serializable {

    /**
     * Service métier générique.
     */
    @Inject
    CommonService service;

    /**
     * Mot clef pour la recherche.
     */
    private String keyword;

    /**
     * Liste des entités recherchées en fonction du mot clef.
     */
    private List<? extends Identifiable<?>> entities;

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
    public void search(DynamicEntity entityType) {
        if (isKeywordEmpty()) {
            this.entities.clear();
        } else {
            // Optimisation des requêtes.
            // Ne lancer la recherche que si le mot clef n'est pas vide.
            this.entities = service.filter(entityType.getEntityClass(), keyword);
        }
    }

    /**
     * Indiquer si le mot clef est vide.
     *
     * @return La valeur {@code true} si le mot clef est nul, vide ou ne
     * comporte q'un seul caractère, sinon la valeur {@code false} est retournée
     */
    public boolean isKeywordEmpty() {
        return Objects.isNull(keyword)
                || keyword.isBlank()
                || keyword.length() <= 1;
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

    public List<? extends Identifiable<?>> getEntities() {
        return entities;
    }

    public void setEntities(List<? extends Identifiable<?>> entities) {
        this.entities = entities;
    }

}
