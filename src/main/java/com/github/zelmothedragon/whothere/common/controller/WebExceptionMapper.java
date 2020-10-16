package com.github.zelmothedragon.whothere.common.controller;

import javax.json.Json;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class WebExceptionMapper implements ExceptionMapper<RuntimeException> {

    public WebExceptionMapper() {
    }

    @Override
    public Response toResponse(final RuntimeException ex) {

        var message = Json
                .createObjectBuilder()
                .add("error", ex.getClass().getCanonicalName())
                .add("message", ex.getMessage())
                .build();

        return Response
                .status(Response.Status.BAD_REQUEST)
                .type(MediaType.APPLICATION_JSON)
                .entity(message)
                .build();
    }

}
