package com.github.zelmothedragon.whothere.common.persistence;

import com.github.zelmothedragon.whothere.common.persistence.AbstractEntity;
import javax.enterprise.context.Dependent;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.json.bind.config.PropertyOrderStrategy;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Un agent, utilisateur de l'application.
 *
 * @author MOSELLE Maxime
 */
@Dependent
@JsonbPropertyOrder(PropertyOrderStrategy.LEXICOGRAPHICAL)
@Entity
@Table(name = "agent")
@Access(AccessType.FIELD)
public class Agent extends AbstractEntity {

    /**
     * Numéro de série.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Prénom.
     */
    @Size(min = 1, max = 255)
    @NotBlank
    @JsonbProperty(value = "givenName", nillable = false)
    @Column(name = "given_name", nullable = false)
    private String givenName;

    /**
     * Nom d'usage.
     */
    @Size(min = 1, max = 255)
    @NotBlank
    @JsonbProperty(value = "familyName", nillable = false)
    @Column(name = "family_name", nullable = false)
    private String familyName;

    /**
     * Adresse de courriel.
     */
    @Size(min = 1, max = 255)
    @Email
    @NotBlank
    @JsonbProperty(value = "email", nillable = false)
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    /**
     * Rang.
     */
    @Size(min = 1, max = 255)
    @NotBlank
    @JsonbProperty(value = "rank", nillable = false)
    @Column(name = "rank", nullable = false)
    private String rank;

    /**
     * Rang abrégé.
     */
    @Size(min = 1, max = 255)
    @NotBlank
    @JsonbProperty(value = "shortRank", nillable = false)
    @Column(name = "short_rank", nullable = false)
    private String shortRank;

    /**
     * Numéro de téléphone professionnel.
     */
    @Size(min = 1, max = 255)
    @NotBlank
    @JsonbProperty(value = "phoneNumber", nillable = false)
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    /**
     * Genre de l'agent.
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;

    /**
     * Constructeur par défaut. Requis pour le fonctionnement des technologies
     * de <i>Jakarta EE</i>.
     */
    public Agent() {
        // Ne pas appeler explicitement.
    }

    // ------------------------------
    // Accesseurs & Mutateurs
    // ------------------------------
    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getShortRank() {
        return shortRank;
    }

    public void setShortRank(String shortRank) {
        this.shortRank = shortRank;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

}
