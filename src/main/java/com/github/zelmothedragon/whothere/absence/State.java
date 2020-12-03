package com.github.zelmothedragon.whothere.absence;

import com.github.zelmothedragon.whothere.common.persistence.AbstractModel;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * État d'absence.
 *
 * @author MOSELLE Maxime
 */
@Entity
@Table(name = "state_of_absence")
@Access(AccessType.FIELD)
public class State extends AbstractModel {

    /**
     * Numéro de série.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Code unique de l'absence.
     */
    @Pattern(regexp = "[A-Z0-9_]*")
    @Size(min = 1, max = 255)
    @NotBlank
    @Column(name = "code", nullable = false, unique = true)
    private String code;

    /**
     * Description synthétique.
     */
    @Size(min = 1, max = 255)
    @NotBlank
    @Column(name = "description", nullable = false)
    private String description;

    /**
     * Indique si un agent est disponible ou non.
     */
    @NotNull
    @Column(name = "available", nullable = false)
    private Boolean available;

    /**
     * Constructeur par défaut. Requis pour le fonctionnement des technologies
     * d'entreprise.
     */
    public State() {
        // Ne pas appeler explicitement.
    }

    // ------------------------------
    // Accesseurs & Mutateurs
    // ------------------------------
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

}
