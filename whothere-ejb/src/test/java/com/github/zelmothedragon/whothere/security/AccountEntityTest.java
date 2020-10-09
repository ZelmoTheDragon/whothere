package com.github.zelmothedragon.whothere.security;

import com.github.zelmothedragon.whothere.Archive;
import javax.inject.Inject;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Test Unitaire sur une entité persistante.
 *
 * @author MOSELLE Maxime
 */
@RunWith(Arquillian.class)
public class AccountEntityTest {

    /**
     * Instance d'entité de test.
     */
    @Inject
    private AccountEntity instance;

    /**
     * Constructeur par défaut. Requis pour le fonctionnement de l'environnement
     * de test.
     */
    public AccountEntityTest() {
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
    public void injectEntity() {
        Assert.assertNotNull(instance);
    }

}
