package com.hendisantika.errorhandle;

import io.quarkus.runtime.util.ExceptionUtil;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.time.LocalDateTime;
import java.util.HashMap;

@Provider
public class ProductExceptionHandler implements ExceptionMapper<RuntimeException> {

    @Override
    public Response toResponse(RuntimeException exception) {
        return Response.status(Status.OK).entity(
                new HashMap<String, String>() {{
                    put("code", "500");
                    put("msg", exception.getMessage());
                    put("timestamp", LocalDateTime.now().toString());
                    put("debugMessage", ExceptionUtil.generateStackTrace(exception));
                }}).build();

    }

}
