package org.fundaciobit.blueprint.rest.resource;

import org.fundaciobit.blueprint.common.constraint.NIF;
import org.fundaciobit.blueprint.ejb.jpa.Item;
import org.fundaciobit.blueprint.ejb.service.ItemService;

import javax.ejb.EJB;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@Path("/item")
public class ItemResource {

    @EJB
    private ItemService itemService;

    @GET
    @Path("all")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Item> findAll() {
        return itemService.findAll();
    }

    @GET
    @Path("byNIFs")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Item> findByNIFs(@QueryParam("nif") List<@NIF String> nifs) {
        return itemService.findByNIFs(nifs);
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response findById(@PathParam("id") Long id) {
        Item item = itemService.findById(id);
        if (item != null) {
            return Response.ok(item).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response create(@Valid Item item) {
        itemService.create(item);
        return Response.created(URI.create("item/"+ item.getId())).build();
    }

}
