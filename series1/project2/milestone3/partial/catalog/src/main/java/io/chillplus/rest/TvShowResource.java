package io.chillplus.rest;

import io.chillplus.domain.TvShow;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/api/tv")
@Produces("application/json")
@Consumes("application/json")
public class TvShowResource {

    @POST
    @Transactional
    public Response create(@Valid TvShow tvShow) {
        if (tvShow.id != null) {
            throw new WebApplicationException("A new entity cannot already have an ID", Response.Status.BAD_REQUEST);
        }
        tvShow.persist();
        return Response.status(Response.Status.CREATED).entity(tvShow).build();
    }

    @PUT
    @Transactional
    public Response update(@Valid TvShow tvShow) {
        if (tvShow.id == null) {
            throw new WebApplicationException("Invalid ID", Response.Status.BAD_REQUEST);
        }
        TvShow updated = TvShow.findById(tvShow.id);
        updated.id = tvShow.id;
        updated.title = tvShow.title;
        updated.category= tvShow.category;
        return Response.ok(updated).build();
    }

    @GET
    public List<TvShow> getAll() {
        return TvShow.listAll();
    }

    @GET
    @Path("/{id}")
    public TvShow getOneById(@PathParam("id") long id) {
        TvShow entity = TvShow.findById(id);
        if (entity == null) {
            throw new WebApplicationException("Entity does not exist. ", Response.Status.NOT_FOUND);
        }
        return entity;
    }

    @DELETE
    @Transactional
    public Response deleteAll() {
        TvShow.deleteAll();
        return Response.ok().build();
    }

    @DELETE
    @Transactional
    @Path("/{id}")
    public Response deleteOne(@PathParam("id") long id) {
        TvShow.deleteById(id);
        return Response.ok().build();
    }

    public TvShow getOneByTitle() {
        return null;
    }

    public List<TvShow> getAllByCategory() {
        return new ArrayList<>();
    }

}
