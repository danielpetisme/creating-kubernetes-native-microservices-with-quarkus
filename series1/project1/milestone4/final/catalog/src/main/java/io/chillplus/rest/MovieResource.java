package io.chillplus.rest;

import io.chillplus.domain.Movie;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/api/movies")
@Produces("application/json")
@Consumes("application/json")
public class MovieResource {

    private List<Movie> movies = new ArrayList<>();

    @POST
    public Response create(@Valid Movie movie) {
        movies.add(movie);
        return Response.status(Response.Status.CREATED).entity(movie).build();
    }

    @GET
    public List<Movie> getAll() {
        return movies;
    }

    @GET
    @Path("/{title}")
    public Movie getOne(@PathParam("title") String title) {
        return movies.stream()
                .filter(it -> it.title.equalsIgnoreCase(title)).findFirst()
                .orElseThrow(() -> new WebApplicationException("Movie: [" + title + "] not found", Response.Status.NOT_FOUND));
    }

    @DELETE
    public Response deleteAll() {
        movies.clear();
        return Response.ok().build();
    }

}
