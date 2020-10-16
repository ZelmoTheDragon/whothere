package com.github.zelmothedragon.whothere.common.controller;

import com.github.zelmothedragon.whothere.common.service.CommonService;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Contrôleur générique pour les opérations de base sur les entités.
 *
 * @author MOSELLE Maxime
 */
@RequestScoped
@Path("entity")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CommonController {

    @Inject
    private CommonService service;

    /**
     * Constructeur par défaut. Requis pour le fonctionnement des technologies
     * de <i>Jakarta EE</i>.
     */
    public CommonController() {
        // Ne pas appeler explicitement.
    }

    @GET
    @Path("{entity}")
    public Response find(@PathParam("entity") final String entityName) {

        var entityClass = DynamicEntityMapper.mapToEntityClass(entityName);
        var entities = service.find(entityClass);

        return Response
                .ok(entities)
                .build();
    }

    @GET
    @Path("{entity}/{id}")
    public Response find(
            @PathParam("entity") final String entityName,
            @PathParam("id") final String id) {

        var entityClass = DynamicEntityMapper.mapToEntityClass(entityName);
        var entityId = DynamicEntityMapper.mapToEntityId(entityName, id);

        return service
                .find(entityClass, entityId)
                .map(e -> Response.ok(e).build())
                .orElseGet(() -> Response.status(Response.Status.NOT_FOUND).build());
    }

    @POST
    @Path("{entity}")
    public Response create(
            @PathParam("entity") final String entityName,
            final String jsonEntity) {

        var entity = DynamicEntityMapper.mapToEntity(entityName, jsonEntity);
        service.save(entity);

        return Response
                .status(Response.Status.CREATED)
                .build();
    }

    @PUT
    @Path("{entity}")
    public Response update(
            @PathParam("entity") final String entityName,
            final String jsonEntity) {

        var entity = DynamicEntityMapper.mapToEntity(entityName, jsonEntity);
        service.save(entity);

        return Response
                .status(Response.Status.NO_CONTENT)
                .build();
    }

    @DELETE
    @Path("{entity}/{id}")
    public Response delete(
            @PathParam("entity") final String entityName,
            @PathParam("id") final String id) {

        var entityClass = DynamicEntityMapper.mapToEntityClass(entityName);
        var entityId = DynamicEntityMapper.mapToEntityId(entityName, id);
        service.remove(entityClass, entityId);

        return Response
                .status(Response.Status.NO_CONTENT)
                .build();
    }

}
