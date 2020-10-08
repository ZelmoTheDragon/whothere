package com.github.zelmothedragon.whothere.security;

import javax.inject.Inject;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
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

    @Inject
    private AccountEntity instance;

    /**
     * Constructeur par défaut. Requis pour le fonctionnement de l'environnement
     * de test.
     */
    public AccountEntityTest() {
        // RAS
    }

    /**
     * Construire une archive à déployer sur le serveur d'application embarqué.
     * Cette archive comporte les classes et la configuration necessaire pour
     * l'éxecution des tests de cette classe.
     *
     * @return Archive à déployer
     */
    @Deployment
    public static WebArchive createDeployment() {
        var archive = ShrinkWrap
                .create(WebArchive.class)
                .addAsWebInfResource("beans.xml")
                .addAsWebInfResource("web.xml")
                .addAsWebInfResource("payara-resources.xml")
                .addAsResource("persistence.xml", "META-INF/persistence.xml")
                .addPackages(true, "com.github.zelmothedragon.whothere");

        System.out.println(archive.toString(true));
        return archive;
    }

    /**
     * Tester l'injection CDI d'une entité.
     */
    @Test
    public void injectEntity() {
        Assert.assertNotNull(instance);
    }

}
