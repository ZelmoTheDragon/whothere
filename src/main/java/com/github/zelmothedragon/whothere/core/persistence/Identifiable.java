package com.github.zelmothedragon.whothere.core.persistence;

/**
 * Interface générique pour toutes les entitiés identifiables par un identifiant
 * unique.
 *
 * @author MOSELLE Maxime
 * @param <K> Type de l'identifiant unique
 */
public interface Identifiable<K> {

    /**
     * Accesseur de l'identifiant unique.
     *
     * @return L'identifiant unique
     */
    K getId();

    /**
     * Mutateur de l'identifiant unique. Cette méthode est présente pour
     * respecter la convention <i>Java Bean</i>, ne pas appeler explicitement.
     *
     * @param id Identifiant unique
     */
    void setId(K id);

    /**
     * Synchroniser l'identifiant unique avec le contexte de persistance.
     *
     * @return La valeur {@code true} si cette instance possède son identifiant
     * unique synchronisé, sinon la valeur {@code false} est retournée
     */
    boolean synchronizeId();
}
