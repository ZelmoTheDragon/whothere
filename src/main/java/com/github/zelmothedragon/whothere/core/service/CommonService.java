package com.github.zelmothedragon.whothere.core.service;

import com.github.zelmothedragon.whothere.core.persistence.Pagination;
import com.github.zelmothedragon.whothere.core.persistence.Identifiable;
import com.github.zelmothedragon.whothere.core.persistence.JPA;
import java.util.List;
import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

/**
 * Implémentation du service commun avec <i>CDI</i>.
 *
 * @author MOSELLE Maxime
 * @see Service
 */
@ApplicationScoped
@Transactional
public class CommonService {

    /**
     * Constructeur par défaut. Requis pour le fonctionnement des technologies
     * de <i>Jakarta EE</i>.
     */
    public CommonService() {
        // Ne pas appeler explicitement.
    }

    /**
     * @see Service#find(Object)
     * @param <E> Type générique de l'entité
     * @param entityClass Classe de l'entité
     * @param id Identifiant unique
     * @return Une option contenant ou non l'entité correspondante à
     * l'identifiant unique
     */
    public <E extends Identifiable<?>> Optional<E> find(
            final Class<E> entityClass,
            final Object id) {

        return JPA.get(entityClass, id);
    }

    /**
     * @see Service#find()
     * @param <E> Type générique de l'entité
     * @param entityClass Classe de l'entité
     * @return Une liste des entités métiers
     */
    public <E extends Identifiable<?>> List<E> find(final Class<E> entityClass) {
        return JPA.get(entityClass);
    }

    /**
     * @see Service#filter(Pagination)
     * @param <E> Type générique de l'entité
     * @param entityClass Classe de l'entité
     * @param pagination Critère de pagination
     * @return Une liste des entités métiers
     */
    public <E extends Identifiable<?>> List<E> filter(
            final Class<E> entityClass,
            final Pagination pagination) {

        return JPA.get(entityClass, pagination);
    }

    /**
     * @see Service#filter(String)
     * @param <E> Type générique de l'entité
     * @param entityClass Classe de l'entité
     * @param keyword Mot clef pour la recherche
     * @return Une liste des entités persistantes
     */
    public <E extends Identifiable<?>> List<E> filter(
            final Class<E> entityClass,
            final String keyword) {

        return JPA.get(entityClass, keyword);
    }

    /**
     * @see Service#exists(Identifiable)
     * @param entity Entité métier
     * @return La valeur {@code true} si l'entité existe, sinon la valeur
     * {@code false} est retournée
     */
    public boolean exists(final Identifiable<?> entity) {
        return JPA.contains(entity);
    }

    /**
     * @see Service#exists(Object)
     * @param entityClass Classe de l'entité
     * @param id Identifiant unique
     * @return La valeur {@code true} si l'entité existe, sinon la valeur
     * {@code false} est retournée
     */
    public boolean exists(
            final Class<? extends Identifiable<?>> entityClass,
            final Object id) {

        return JPA.contains(entityClass, id);
    }

    /**
     * @see Service#save(Identifiable)
     * @param <E> Type générique de l'entité
     * @param entity Entité métier
     * @return L'entité sauvegarée
     */
    public <E extends Identifiable<?>> E save(final E entity) {
        entity.checkId();
        return JPA.add(entity);
    }

    /**
     * @see Service#remove(Identifiable)
     * @param entity Entité métier
     */
    public void remove(final Identifiable<?> entity) {
        JPA.remove(entity);
    }

    /**
     * @see Service#remove(Object)
     * @param entityClass Classe de l'entité
     * @param id Identifiant unique
     */
    public void remove(
            final Class<? extends Identifiable<?>> entityClass,
            final Object id) {

        var entity = JPA.get(entityClass, id);
        entity.ifPresent(e -> JPA.remove(e));
    }

}
