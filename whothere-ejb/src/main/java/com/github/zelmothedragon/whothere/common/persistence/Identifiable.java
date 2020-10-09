package com.github.zelmothedragon.whothere.common.persistence;

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
}
