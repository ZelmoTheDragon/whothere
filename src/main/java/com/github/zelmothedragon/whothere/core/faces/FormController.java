package com.github.zelmothedragon.whothere.core.faces;

import com.github.zelmothedragon.whothere.DynamicEntity;
import com.github.zelmothedragon.whothere.core.service.CommonService;
import com.github.zelmothedragon.whothere.core.persistence.Identifiable;
import java.io.Serializable;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Contrôleur générique pour la recherche de données par mot clef et les
 * opération de base sur les entités.
 *
 * @author MOSELLE Maxime
 */
@Named
@ViewScoped
public class FormController implements Serializable {

    /**
     * Service métier générique.
     */
    @Inject
    CommonService service;

    /**
     * Type de l'entité métier.
     */
    private DynamicEntity entityType;

    /**
     * Constructeur d'injection. Requis pour le fonctionnement des technologies
     * de Java EE.
     */
    public FormController() {
        // Ne pas appeler explicitement.
    }

    /**
     * Enregister une entité. (Ajout ou modification)
     *
     * @param entity Entité métier à enregistrer
     */
    public void save(final Identifiable<?> entity) {
        System.out.println("ENTITY: " + entity);
        this.service.save(entity);
    }




}
