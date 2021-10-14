package com.hendisantika.restapi;

import com.hendisantika.entity.Product;
import io.quarkus.runtime.StartupEvent;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.json.Json;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * Project : quarkus-simple-rest-api
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 14/10/21
 * Time: 12.49
 */
@Path("api/v1/products")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class ProductResource {

    @Inject
    EntityManager entityManager;

    @GET
    public Product[] get() {
//        System.out.println(new SimpleDateFormat("HH:mm:ss.SSS").format(new Date()));
        return entityManager.createNamedQuery("Product.findAll", Product.class)
                .getResultList().toArray(new Product[0]);
    }

    @GET
    @Path("{id}")
    public Product getSingle(@PathParam Integer id) {
        Product entity = entityManager.find(Product.class, id);
        if (entity == null) {
            throw new WebApplicationException("Product with id of " + id + " does not exist.", 404);
        }
        return entity;
    }

    @POST
    @Transactional
    public Response create(Product Product) {
        if (Product.getId() != null) {
            throw new WebApplicationException("Id was invalidly set on request.", 422);
        }

        entityManager.persist(Product);
        return Response.ok(Product).status(201).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public Product update(@PathParam Integer id, Product Product) {
        if (Product.getName() == null) {
            throw new WebApplicationException("Product Name was not set on request.", 422);
        }

        Product entity = entityManager.find(Product.class, id);

        if (entity == null) {
            throw new WebApplicationException("Product with id of " + id + " does not exist.", 404);
        }

        entity.setName(Product.getName());

        return entity;
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Response delete(@PathParam Integer id) {
        Product entity = entityManager.getReference(Product.class, id);
        if (entity == null) {
            throw new WebApplicationException("Product with id of " + id + " does not exist.", 404);
        }
        entityManager.remove(entity);
        return Response.status(204).build();
    }

    void onStart(@Observes StartupEvent startup) {
        System.out.println(new SimpleDateFormat("HH:mm:ss.SSS").format(new Date()));
    }

    @Provider
    public static class ErrorMapper implements ExceptionMapper<Exception> {

        @Override
        public Response toResponse(Exception exception) {
            int code = 500;
            if (exception instanceof WebApplicationException) {
                code = ((WebApplicationException) exception).getResponse().getStatus();
            }
            return Response.status(code)
                    .entity(Json.createObjectBuilder().add("error", exception.getMessage()).add("code", code).build())
                    .build();
        }

    }
}

