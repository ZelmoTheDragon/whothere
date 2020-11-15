package com.github.zelmothedragon.whothere.core.faces;

import com.github.zelmothedragon.whothere.DynamicEntity;
import com.github.zelmothedragon.whothere.core.persistence.Identifiable;
import com.github.zelmothedragon.whothere.core.service.CommonService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Contrôleur générique pour la recherche de données par mot clef.
 *
 * @author MOSELLE Maxime
 */
@Named
@SessionScoped
public class CommonController implements Serializable {

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
     * Entité en cours de création ou modification.
     */
    private Identifiable<?> entity;

    /**
     * Constructeur d'injection. Requis pour le fonctionnement des technologies
     * de Jakarta EE.
     */
    public CommonController() {
        this.entities = new ArrayList<>();
        // Ne pas appeler explicitement.
    }

    /**
     * Rechercher un élément dans le site.
     *
     * @param entityType Type dynamique de l'entité métier
     */
    public void search(final DynamicEntity entityType) {
        if (isKeywordEmpty()) {
            this.entities.clear();
        } else {
            // Optimisation des requêtes.
            // Ne lancer la recherche que si le mot clef n'est pas vide.
            this.entities = service.filter(entityType.getEntityClass(), keyword);
        }
    }

    /**
     * Enregister une entité. (Ajout ou modification)
     */
    public void save() {
        System.out.println("SAVE: " + entity);
        this.service.save(entity);
    }

    /**
     * Passer le formulaire en mode édition.
     * @param entity Entité à modifier
     */
    public void edit(final Identifiable<?> entity) {
        this.entity = entity;
        System.out.println("EDIT: " + entity);
    }

    /**
     * Passer le formulaire en mode création
     * @param entityType Type de l'entité
     */
    public void create(final DynamicEntity entityType) {
        this.entity = entityType.newInstance();
        System.out.println("CREATE: " + entity);
    }

    /**
     * Supprimer l'entité métier.
     *
     * @param entity Entité à supprimer
     */
    public void remove(final Identifiable<?> entity) {
        this.service.remove(entity);
        this.entities.remove(entity);
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

    public Identifiable<?> getEntity() {
        return entity;
    }

    public void setEntity(Identifiable<?> entity) {
        this.entity = entity;
    }

}
