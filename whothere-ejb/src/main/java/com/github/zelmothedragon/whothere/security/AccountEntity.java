package com.github.zelmothedragon.whothere.security;

import com.github.zelmothedragon.whothere.common.persistence.AbstractEntity;
import java.util.HashSet;
import java.util.Set;
import javax.enterprise.context.Dependent;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.json.bind.annotation.JsonbTransient;
import javax.json.bind.config.PropertyOrderStrategy;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Compte utilisateur.
 *
 * @author MOSELLE Maxime
 */
@Dependent
@JsonbPropertyOrder(PropertyOrderStrategy.LEXICOGRAPHICAL)
@Entity
@Table(name = "account")
@Access(AccessType.FIELD)
public class AccountEntity extends AbstractEntity {

    /**
     * Numéro de série.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Identifiant de connexion.
     */
    @Size(min = 2, max = 255)
    @NotBlank
    @JsonbProperty(value = "username", nillable = false)
    @Column(name = "username", unique = true)
    private String username;

    /**
     * Mot de passe (haché).
     */
    @NotBlank
    @JsonbTransient
    @Column(name = "password")
    private String password;

    /**
     * Indique si le compte est bloqué.
     */
    @NotNull
    @JsonbProperty(value = "locked", nillable = false)
    @Column(name = "locked", nullable = false)
    private Boolean locked;

    /**
     * Rôles.
     */
    @NotNull
    @JsonbProperty(value = "roles", nillable = false)
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "account_role",
            joinColumns = @JoinColumn(name = "account_id"),
            foreignKey = @ForeignKey(name = "fk_account_role")
    )
    @Column(name = "role_name", nullable = false)
    private Set<@NotEmpty @Size(max = 255) String> roles;

    /**
     * Constructeur par défaut. Requis pour le fonctionnement des technologies
     * de <i>Jakarta EE</i>.
     */
    public AccountEntity() {
        this.roles = new HashSet<>();
        // Ne pas appeler explicitement.
    }

    // ------------------------------
    // Accesseurs & Mutateurs
    // ------------------------------
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public Set<String> getRoles() {
        return Set.copyOf(roles);
    }

    public void setRoles(Set<String> roles) {
        this.roles.clear();
        this.roles.addAll(roles);
    }

}
