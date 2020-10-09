package com.github.zelmothedragon.whothere.security;

import javax.enterprise.context.ApplicationScoped;
import javax.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;
import javax.security.enterprise.identitystore.IdentityStore.ValidationType;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;

/**
 * Entrepôt de données pour la récupération de l'utilisateur en session ainsi
 * que ses rôles associés.
 *
 * @author MOSELLE Maxime
 */
@ApplicationScoped
@DatabaseIdentityStoreDefinition(
        callerQuery = LocalIdentityStore.CALLER_QUERY,
        groupsQuery = LocalIdentityStore.GROUPS_QUERY,
        dataSourceLookup = LocalIdentityStore.DATA_SOURCE,
        useFor = {ValidationType.VALIDATE, ValidationType.PROVIDE_GROUPS},
        hashAlgorithm = Pbkdf2PasswordHash.class
)
public class LocalIdentityStore {

    /**
     * Requête SQL pour récupérer le mot de passe d'un utilisateur.
     */
    static final String CALLER_QUERY = ""
            + "SELECT a.password "
            + "FROM account a "
            + "WHERE a.username = ? AND a.locked = FALSE;";

    /**
     * Requête SQL pour récupérer les rôles.
     */
    static final String GROUPS_QUERY = ""
            + "SELECT r.role_name "
            + "FROM account_role r "
            + "INNER JOIN account a ON a.id = r.account_id "
            + "WHERE a.username = ? AND a.locked = FALSE;";

    /**
     * Ressource de la base de données. Voir {@code persistence.xml}.
     */
    static final String DATA_SOURCE = "java:app/jdbc/WhoThereDS";

    /**
     * Constructeur par défaut. Requis pour le fonctionnement des technologies
     * de <i>Jakarta EE</i>.
     */
    public LocalIdentityStore() {
        // Ne pas appeler explicitement.
    }

}
