package org.fundaciobit.blueprint.rest;

import io.swagger.v3.jaxrs2.integration.JaxrsOpenApiContextBuilder;
import io.swagger.v3.oas.integration.OpenApiConfigurationException;

import javax.annotation.PostConstruct;
import javax.servlet.ServletConfig;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import java.util.logging.Logger;

@ApplicationPath("/resource")
public class BlueprintApplication extends Application {

    private static final Logger log = Logger.getLogger(BlueprintApplication.class.getName());

    @Context
    ServletConfig servletConfig;

    public BlueprintApplication() {}

    @PostConstruct
    public void init() {
        log.info("Init REST Application");
        try {
            new JaxrsOpenApiContextBuilder<>()
                    .servletConfig(servletConfig)
                    .application(this)
                    .configLocation("openapi-configuration.json")
                    .buildContext(true);
        } catch (OpenApiConfigurationException e) {
            log.severe("Error inicialitzant OpenApi " + e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
