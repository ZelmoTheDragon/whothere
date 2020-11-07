package com.github.zelmothedragon.whothere.common.faces;

import java.util.ResourceBundle;
import javax.faces.application.ProjectStage;
import javax.faces.application.ViewHandler;
import javax.faces.validator.BeanValidator;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Classe de configuration du contexte <i>Servlet</i>.
 *
 * @author MOSELLE Maxime
 */
@WebListener
public class StartupListener implements ServletContextListener {

    /**
     * Constructeur par défaut. Requis pour le fonctionnement des technologies
     * de <i>Jakarta EE</i>.
     */
    public StartupListener() {
        // Ne pas appeler explicitement.
    }

    @Override
    public void contextInitialized(final ServletContextEvent context) {

        var config = ResourceBundle.getBundle("application");
        setContextParam(config, context, FacesConfiguration.CONFIG_PRIMEFACES_THEME);
        setContextParam(config, context, ProjectStage.PROJECT_STAGE_PARAM_NAME);
        setContextParam(config, context, ViewHandler.FACELETS_SKIP_COMMENTS_PARAM_NAME);
        setContextParam(config, context, BeanValidator.ENABLE_VALIDATE_WHOLE_BEAN_PARAM_NAME);
    }

    private static void setContextParam(
            final ResourceBundle config,
            final ServletContextEvent context,
            final String key) {

        var contextParam = config.getString(key);
        context.getServletContext().setInitParameter(key, contextParam);
    }

}
