package com.github.zelmothedragon.whothere.core.service;

import com.github.zelmothedragon.whothere.core.persistence.Identifiable;
import com.github.zelmothedragon.whothere.core.persistence.Pagination;
import java.util.Collection;
import java.util.Optional;

/**
 * Service métier commun.
 *
 * @param <E> Type d'entité métier
 * @param <K> Type d'identifiant unique
 * @author MOSELLE Maxime
 */
public interface Service<E extends Identifiable<K>, K> {

    /**
     * Rechercher une entité en fonction de son identifiant unique.
     *
     * @param id Identifiant unique
     * @return Une option contenant ou non l'entité correspondante à
     * l'identifiant unique
     */
    Optional<E> find(K id);

    /**
     * Rechercher toutes les entités enregistrés.
     *
     * @return Une liste des entités métiers
     */
    Collection<E> find();

    /**
     * Rechercher des entités enregistrés avec un critère de pagination.
     *
     * @param pagination Critère de pagination
     * @return Une collection des entités métiers
     */
    Collection<E> find(Pagination pagination);

    /**
     * Vérifier l'existence d'une entité.
     *
     * @param entity Entité métier
     * @return La valeur {@code true} si l'entité existe, sinon la valeur
     * {@code false} est retournée
     */
    boolean exists(E entity);

    /**
     * Vérifier l'existence d'une entité en fonction de son identifiant.
     *
     * @param id Identifiant unique
     * @return La valeur {@code true} si l'entité existe, sinon la valeur
     * {@code false} est retournée
     */
    boolean exists(K id);

    /**
     * Sauvegarder une nouvelle entité. Si l'entité existe déjà, elle sera mise
     * à jour.
     *
     * @param entity Entité métier
     * @return L'entité sauvegarée
     */
    E save(E entity);

    /**
     * Supprimer une entité.
     *
     * @param entity Entité métier
     */
    void remove(E entity);

    /**
     * Supprimer une entité en fonction de son identifiant.
     *
     * @param id Identifiant unique
     */
    void remove(K id);
}
