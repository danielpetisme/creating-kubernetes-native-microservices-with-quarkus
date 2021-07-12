package io.chillplus.rest;

import io.chillplus.domain.TvShow;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/api/tv")
@Produces("application/json")
@Consumes("application/json")
public class TvShowResource {

    private long sequence = 0;
    private List<TvShow> tvShows = new ArrayList<>();

    @POST
    public Response create(@Valid TvShow tvShow) {
        if (tvShow.id != null) {
            throw new WebApplicationException("A new entity cannot already have an ID", Response.Status.BAD_REQUEST);
        }
        tvShow.id = sequence;
        sequence++;
        tvShows.add(tvShow);
        return Response.status(Response.Status.CREATED).entity(tvShow).build();
    }

    @GET
    public List<TvShow> getAll() {
        return tvShows;
    }

    @GET
    @Path("/{id}")
    public TvShow getOneById(@PathParam("id") long id) {
        TvShow entity = null;
        for (int i = 0; i < tvShows.size(); i++) {
            TvShow tvShow = tvShows.get(i);
            if (tvShow.id == id) {
                entity = tvShow;
                break;
            }
        }
        if (entity == null) {
            throw new WebApplicationException("Entity does not exist. ", Response.Status.NOT_FOUND);
        }
        return entity;
    }

    @DELETE
    public Response deleteAll() {
        tvShows.clear();
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteOne(@PathParam("id") long id) {
        int index = 0;
        for (; index < tvShows.size(); index++) {
            TvShow tvShow = tvShows.get(index);
            if (tvShow.id == id) {
                break;
            }
        }
        if (index < tvShows.size()) {
            tvShows.remove(index);
        }
        return Response.ok().build();
    }

}
