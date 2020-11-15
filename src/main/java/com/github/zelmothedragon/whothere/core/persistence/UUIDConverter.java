package com.github.zelmothedragon.whothere.core.persistence;

import java.util.Objects;
import java.util.UUID;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Convertisseur d'attribut pour le type {@link java.util.UUID} vers
 * {@link java.lang.String} et vice versa pour la base de données. Cette classe
 * est invoquée automatiquement par <i>JPA</i>.
 *
 * @author MOSELLE Maxime
 */
@Converter(autoApply = true)
public class UUIDConverter implements AttributeConverter<UUID, String> {

    /**
     * Taille de la colonne pour le stockage en base de données.
     */
    public static final int UUID_SIZE = 36;

    /**
     * Type de la colonne pour le stockage en base de données.
     */
    public static final String UUID_COLUMN = "VARCHAR(36)";

    /**
     * Constructeur par défaut. Requis pour le fonctionnement des technologies
     * de <i>Jakarta EE</i>.
     */
    public UUIDConverter() {
        // Ne pas appeler explicitement.
    }

    /**
     * Convertir un attribut de type {@link java.util.UUID} vers un type
     * {@link java.lang.String}.
     *
     * @param attribute Attribut d'une entité persistante
     * @return La représentation textuelle d'un type {@link java.util.UUID} ou
     * {@code null}
     */
    @Override
    public String convertToDatabaseColumn(final UUID attribute) {
        String dbData;
        if (Objects.nonNull(attribute)) {
            dbData = attribute.toString();
        } else {
            dbData = null;
        }
        return dbData;
    }

    /**
     * Convertir une colonne de type assignable à {@link java.lang.String} vers
     * un type {@link java.util.UUID}.
     *
     * @param dbData Colonne d'une table en base de données
     * @return La représentation d'un type {@link java.util.UUID} ou
     * {@code null}
     */
    @Override
    public UUID convertToEntityAttribute(final String dbData) {
        UUID attribute;
        if (Objects.nonNull(dbData)) {
            attribute = UUID.fromString(dbData);
        } else {
            attribute = null;
        }
        return attribute;
    }

}
