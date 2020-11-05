package com.github.zelmothedragon.whothere.common.controller;

import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author MOSELLE Maxime
 */
@RequestScoped
@Path("query")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class QueryController {

    /**
     * Constructeur par défaut. Requis pour le fonctionnement des technologies
     * de <i>Jakarta EE</i>.
     */
    public QueryController() {
        // Ne pas appeler explicitement.
    }

    @GET
    @Path("{entity}")
    public Response search(
            @PathParam("entity") final String entityName,
            @QueryParam("keyword") final String keyword,
            @QueryParam("field") final List<String> fields,
            @QueryParam("fetch") final List<String> fetches
    ) {
        
        

        return Response.ok().build();
    }
}
