package com.github.zelmothedragon.whothere.common.session;

import com.github.zelmothedragon.whothere.common.persistence.Identifiable;
import java.util.Collection;
import java.util.Optional;
import javax.ejb.Local;

/**
 * Service métier commun.
 *
 * @param <E> Type d'entité métier
 * @param <K> Type d'identifiant unique
 * @author MOSELLE Maxime
 */
@Local
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
     * Chercher toutes les entités enregistrés.
     *
     * @return Une liste des entités métiers
     */
    Collection<E> find();

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
