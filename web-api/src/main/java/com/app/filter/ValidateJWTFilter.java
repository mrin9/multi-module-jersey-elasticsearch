package com.app.filter;

import com.app.model.response.BaseResponse;
import com.app.model.response.BaseResponse.MessageTypeEnum;
import com.app.model.user.Role;
import com.app.model.user.User;
import com.app.service.TokenService;
import java.io.IOException;
import java.lang.reflect.Method;
import java.security.Principal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Priority;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

@Log4j2
@Provider
@Priority(1)
public class ValidateJWTFilter implements ContainerRequestFilter {
    @Context 
    HttpServletRequest request;

    @Context
    ResourceInfo resourceInfo;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        Method method = resourceInfo.getResourceMethod();
        String className = method.getDeclaringClass().getName();
        BaseResponse resp = new BaseResponse();

        //Allow Access for @PermitAll or SwagerSpec
        if (requestContext.getMethod().equalsIgnoreCase("OPTIONS") ||  method == null || method.isAnnotationPresent(PermitAll.class) || className.equals("io.swagger.jaxrs.listing.ApiListingResource")) {
            return;
        }

        String jwtToken = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (StringUtils.isBlank(jwtToken) || TokenService.isValid(jwtToken) ==false ) {
            resp.setTypeAndMessage(MessageTypeEnum.NO_ACCESS, "Invalid Token" );
            //throw new WebApplicationException(Response.status(Response.Status.UNAUTHORIZED).type(MediaType.APPLICATION_JSON).entity(resp).build());
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).type(MediaType.APPLICATION_JSON).entity(resp).build());
            return;
        }

        final User userFromToken;
        try {
             userFromToken = TokenService.getUserFromToken(jwtToken);
        }
        catch (Exception e) {
            resp.setTypeAndMessage(MessageTypeEnum.NO_ACCESS, "Invalid User" );
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).type(MediaType.APPLICATION_JSON).entity(resp).build());
            return;
        }

        // Store User in the SecurityContext
        final SecurityContext securityContext = requestContext.getSecurityContext();
        requestContext.setSecurityContext(new SecurityContext() {
            @Override
            public Principal getUserPrincipal() {return userFromToken;}

            @Override
            public boolean isUserInRole(String role) {return securityContext.isUserInRole(role);}

            @Override
            public boolean isSecure() {return securityContext.isSecure();}

            @Override
            public String getAuthenticationScheme() {return "Token-Based-Auth-Scheme";}
        });

        
        // Everything is permitted for the role "admin"
        if (userFromToken.getRole().toString().equalsIgnoreCase(Role.ADMIN.toString())){
            return;
        }

        //check for roles (If any end-point is annotated with RolesAllowed, then check further for access )
        if(method.isAnnotationPresent(RolesAllowed.class)) {
            RolesAllowed rolesAnnotation = method.getAnnotation(RolesAllowed.class);
            Set<String> allowedRoleSet = new HashSet<String>(Arrays.asList(rolesAnnotation.value() ));
            if (allowedRoleSet.contains(userFromToken.getRole().toString())){
                return;
            }
            else{
                resp.setTypeAndMessage(MessageTypeEnum.NO_ACCESS, "Unauthorized for " +  userFromToken.getRole() + " role");
        	requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).type(MediaType.APPLICATION_JSON).entity(resp).build());
                return;
            }
        }


    }
}
