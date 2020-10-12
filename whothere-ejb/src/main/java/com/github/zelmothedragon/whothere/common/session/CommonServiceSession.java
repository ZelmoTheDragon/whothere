package com.github.zelmothedragon.whothere.common.session;

import com.github.zelmothedragon.whothere.common.persistence.Identifiable;
import com.github.zelmothedragon.whothere.common.persistence.JPA;
import java.io.Serializable;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

/**
 * Implémentation du service commun avec <i>EJB</i>.
 *
 * @author MOSELLE Maxime
 */
@Stateless
public class CommonServiceSession implements Serializable {

    /**
     * Numéro de série.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructeur par défaut. Requis pour le fonctionnement des technologies
     * de <i>Jakarta EE</i>.
     */
    public CommonServiceSession() {
        // Ne pas appeler explicitement.
    }

    @TransactionAttribute(TransactionAttributeType.NEVER)
    public Optional<Identifiable<UUID>> find(final Class<Identifiable<UUID>> entityClass, final UUID id) {
        return JPA.get(entityClass, id);
    }

    @TransactionAttribute(TransactionAttributeType.NEVER)
    public Collection<Identifiable<UUID>> find(final Class<Identifiable<UUID>> entityClass) {
        return JPA.get(entityClass);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Identifiable<UUID> save(final Identifiable<UUID> entity) {
        return JPA.add(entity);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void remove(final Identifiable<UUID> entity) {
        JPA.remove(entity);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void remove(final Class<Identifiable<UUID>> entityClass, UUID id) {
        JPA.remove(id);
    }

}
