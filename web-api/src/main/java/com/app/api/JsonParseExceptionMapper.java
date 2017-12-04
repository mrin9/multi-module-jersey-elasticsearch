package com.app.api;

import com.fasterxml.jackson.core.JsonParseException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class JsonParseExceptionMapper implements ExceptionMapper<JsonParseException>{
    @Override
    public Response toResponse(JsonParseException exception){
        return Response
            .status(Response.Status.BAD_REQUEST)
            .entity("This is an invalid json. The request can not be parsed")
            .type( MediaType.APPLICATION_JSON)
            .build();
    }
    
}