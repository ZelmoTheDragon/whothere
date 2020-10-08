package com.github.zelmothedragon.whothere.common.persistence;

import java.util.Collection;
import java.util.Optional;

/**
 * Entrepôt de données. Le nommage des méthodes est inspirées de
 * <code>java.util.Collection</code>.
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
     * @return La valeur <code>true</code> si l'entité existe, sinon la valeur
     * <code>false</code> est retournée
     */
    boolean contains(E entity);

    /**
     * Vérifier l'existence d'une collection d'entités.
     *
     * @param entities Collection d'entités
     * @return La valeur <code>true</code> si toute les entités de la collection
     * existe, sinon la valeur <code>false</code> est retournée
     */
    boolean containsAll(Collection<E> entities);

    /**
     * Indiquer si aucune occurence existe.
     *
     * @return La valeur <code>true</code> si aucune occurrence existe, sinon la
     * valeur <code>false</code> est retournée
     */
    boolean isEmpty();

    /**
     * Obtenir le nombre d'occurence enregistré.
     *
     * @return Le nombre d'occurrence
     */
    long size();

    /**
     * Ajouter une nouvelle entité. Si l'entité existe déjà, il sera mise à
     * jour.
     *
     * @param entity Entité persistante.
     * @return L'entité persisté
     */
    E add(E entity);

    /**
     * Ajouter une nouvelle collection d'entités.Si les entités existent déjà,
     * il seront mise à jour.
     *
     * @param entities
     * @return Les entités persistées
     */
    Collection<E> addAll(Collection<E> entities);

    /**
     * Supprimer une entité.
     *
     * @param entity Entité persistante à supprimer
     */
    void remove(E entity);

    /**
     * Supprimer une collection d'entité.
     *
     * @param entities Collection d'entités persistantes à supprimer
     */
    void removeAll(Collection<E> entities);

    /**
     * Rechercher une entité en fonction de son identifiant unique.
     *
     * @param id Clef primaire
     * @return Une option contenant ou non l'entité correspondante à
     * l'identifiant unique
     */
    Optional<E> get(K id);

    /**
     * Chercher toutes les entités enregistrés.
     *
     * @return Une liste des entités persistantes
     */
    Collection<E> get();

}
