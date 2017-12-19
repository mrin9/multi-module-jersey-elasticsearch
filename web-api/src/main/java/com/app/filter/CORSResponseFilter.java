package com.app.filter;

import java.io.IOException;
import javax.annotation.Priority;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;


@Priority(0)
public class CORSResponseFilter  implements ContainerResponseFilter {
    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        MultivaluedMap<String, Object> respHeaderMap = responseContext.getHeaders();
        String reqHeaderString = requestContext.getHeaderString("Access-Control-Request-Headers");

        respHeaderMap.add("Access-Control-Allow-Origin", "*");
        respHeaderMap.add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
        respHeaderMap.add("Access-Control-Allow-Credentials", "true");
        respHeaderMap.add("Access-Control-Allow-Headers","Origin, X-Requested-With, Content-Type, Accept, Accept-Encoding, Accept-Language, Host, Referer, Connection, User-Agent, authorization, sw-useragent");
        //respHeaderMap.add("Access-Control-Allow-Headers", reqHeaderString);
        respHeaderMap.add("X-Powered-By", "SonicWALL");
        return;
    }
}
