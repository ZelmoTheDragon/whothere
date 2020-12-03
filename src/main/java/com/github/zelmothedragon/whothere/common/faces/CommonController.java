package com.github.zelmothedragon.whothere.common.faces;

import com.github.zelmothedragon.whothere.DynamicEntity;
import com.github.zelmothedragon.whothere.DynamicView;
import com.github.zelmothedragon.whothere.common.persistence.Repository;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

/**
 * Contrôleur générique pour la recherche de données par mot clef.
 *
 * @author MOSELLE Maxime
 */
@Named
@SessionScoped
@Transactional
public class CommonController implements Serializable {

    @Inject
    Repository repository;
    
    /**
     * Type dynamique de l'entité métier.
     */
    private DynamicEntity entityType;

    /**
     * Mot clef pour la recherche.
     */
    private String keyword;

    /**
     * Liste des entités recherchées en fonction du mot clef.
     */
    private List<?> entities;

    /**
     * Entité en cours de création ou modification.
     */
    private Object entity;

    /**
     * Constructeur par défaut. Requis pour le fonctionnement des technologies
     * d'entreprise.
     */
    public CommonController() {
        this.entities = new ArrayList<>();
        // Ne pas appeler explicitement.
    }

    /**
     * Rechercher un élément dans le site.
     */
    public void search() {
        if (isKeywordEmpty()) {
            this.entities.clear();
        } else {
            // Optimisation des requêtes.
            // Ne lancer la recherche que si le mot clef n'est pas vide.
            this.entities = repository.filter(entityType.getEntityClass(), keyword);
        }
    }

    /**
     * Enregistrer une entité.(Ajout ou modification)
     *
     * @return
     */
    public String save() {
        this.repository.add(entity);

        return DynamicView
                .fromEntityType(entityType)
                .map(DynamicView::getDatatable)
                .orElse(Page.INDEX)
                .redirect();
    }

    /**
     * Passer le formulaire en mode édition.
     *
     * @param entity Entité à modifier
     * @return
     */
    public String edit(final Object entity) {
        this.entity = entity;

        return DynamicView
                .fromEntityType(entityType)
                .map(DynamicView::getForm)
                .orElse(Page.INDEX)
                .redirect();
    }

    /**
     * Passer le formulaire en mode création
     *
     * @return
     */
    public String create() {
        this.entity = entityType.newInstance();
        return DynamicView
                .fromEntityType(entityType)
                .map(DynamicView::getForm)
                .orElse(Page.INDEX)
                .redirect();
    }

    /**
     * Supprimer l'entité métier.
     *
     * @param entity Entité à supprimer
     */
    public void remove(final Object entity) {
        this.repository.remove(entity);
        this.entities.remove(entity);
    }

    /**
     * Indiquer si le mot clef est vide.
     *
     * @return La valeur {@code true} si le mot clef est nul, vide ou ne
     * comporte qu'un seul caractère, sinon la valeur {@code false} est retournée
     */
    public boolean isKeywordEmpty() {
        return Objects.isNull(keyword)
                || keyword.isBlank()
                || keyword.length() <= 1;
    }

    // ------------------------------
    // Accesseurs & Mutateurs
    // ------------------------------
    public DynamicEntity getEntityType() {
        return entityType;
    }

    public void setEntityType(DynamicEntity entityType) {
        this.entityType = entityType;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public List<?> getEntities() {
        return entities;
    }

    public void setEntities(List<?> entities) {
        this.entities = entities;
    }

    public Object getEntity() {
        return entity;
    }

    public void setEntity(Object entity) {
        this.entity = entity;
    }

}
