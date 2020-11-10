package com.github.zelmothedragon.whothere.core.persistence;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Entrepôt de données. Le nommage des méthodes est inspirées de
 * {@link java.util.Collection}.
 *
 * @param <E> Type d'entité persistante
 * @param <K> Type de l'identifiant unique
 * @author MOSELLE Maxime
 */
public interface Repository<E extends Identifiable<K>, K> {

    /**
     * Vérifier l'existence d'une entité.
     *
     * @param entity Entité persistante
     * @return La valeur {@code true} si l'entité existe, sinon la valeur
     * {@code false} est retournée
     */
    boolean contains(E entity);

    /**
     * Vérifier l'existence d'une entité en fonction de son identifiant unique.
     *
     * @param id Identifiant unique
     * @return La valeur {@code true} si l'entité existe, sinon la valeur
     * {@code false} est retournée
     */
    boolean contains(K id);

    /**
     * Vérifier l'existence d'une collection d'entités.
     *
     * @param entities Collection d'entités persistantes
     * @return La valeur {@code true} si toute les entités de la collection
     * existe, sinon la valeur {@code false} est retournée
     */
    boolean containsAll(Collection<E> entities);

    /**
     * Indiquer si aucune occurence existe.
     *
     * @return La valeur {@code true} si aucune occurrence existe, sinon la
     * valeur {@code false} est retournée
     */
    boolean isEmpty();

    /**
     * Obtenir le nombre d'occurence enregistré.
     *
     * @return Le nombre d'occurrence
     */
    long size();

    /**
     * Ajouter une nouvelle entité. Si l'entité existe déjà, elle sera mise à
     * jour.
     *
     * @param entity Entité persistante
     * @return L'entité persistée
     */
    E add(E entity);

    /**
     * Ajouter une nouvelle collection d'entités.Si les entités existent déjà,
     * il seront mise à jour.
     *
     * @param entities Collection d'entités persistantes
     * @return Une collection d'entités persistées
     */
    Collection<E> addAll(Collection<E> entities);

    /**
     * Supprimer une entité.
     *
     * @param entity Entité persistante
     */
    void remove(E entity);

    /**
     * Supprimer une entité en fonction de son identifiant.
     *
     * @param id Identifiant unique.
     */
    void remove(K id);

    /**
     * Supprimer une collection d'entité.
     *
     * @param entities Collection d'entités persistantes
     */
    void removeAll(Collection<E> entities);

    /**
     * Rechercher une entité en fonction de son identifiant unique.
     *
     * @param id Clef primaire
     * @return Une option contenant ou non l'entité correspondante à
     * l'identifiant unique
     */
    Optional<E> find(K id);

    /**
     * Rechercher toutes les entités enregistrés.
     *
     * @return Une liste des entités persistantes
     */
    List<E> get();

    /**
     * Rechercher des entités enregistrées avec un critère de pagination.
     *
     * @param pagination Critère de pagination
     * @return Une liste des entités persistantes
     */
    List<E> filter(Pagination pagination);

    /**
     * Rechercher des entités enregistrées en fonction d'un mot clef.
     *
     * @param keyword Mot clef pour la recherche
     * @return Une liste des entités persistantes
     */
    List<E> filter(String keyword);

}
