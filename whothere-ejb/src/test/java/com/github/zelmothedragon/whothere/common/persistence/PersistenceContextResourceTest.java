package com.github.zelmothedragon.whothere.common.persistence;

import com.github.zelmothedragon.whothere.Archive;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Tests unitaires sur le gestionnaire d'entité.
 *
 * @author MOSELLE Maxime
 */
@RunWith(Arquillian.class)
public class PersistenceContextResourceTest {

    /**
     * Gestionnaire d'entité par défaut.
     */
    @Inject
    private EntityManager em;

    /**
     * Instance d'entrepôt de test.
     */
    @Inject
    private FooDAO fooDAO;

    /**
     * Instance d'entrepôt de test.
     */
    @Inject
    private BarDAO barDAO;

    /**
     * Constructeur par défaut. Requis pour le fonctionnement de l'environnement
     * de test.
     */
    public PersistenceContextResourceTest() {
        // Ne pas appeler explicitement.
    }

    /**
     * Initialisation pour <i>Arquillian</i>.
     *
     * @return Une archive à déployer
     */
    @Deployment
    public static WebArchive createDeployment() {
        return Archive.createDeployment();
    }

    /**
     * Tester l'injection CDI.
     */
    @Test
    public void injectEntityManager() {
        Assert.assertNotNull(em);
    }

    /**
     * Tester l'injection CDI.
     */
    @Test
    public void createEntityManager() {
        var instance = CDI.current().select(EntityManager.class);
        Assert.assertNotNull(instance);
    }

    /**
     * Comparer deux gestionnaires d'entités.
     */
    @Test
    public void compareEntityManager() {
        Assert.assertEquals(fooDAO.em, barDAO.em);
    }

}
