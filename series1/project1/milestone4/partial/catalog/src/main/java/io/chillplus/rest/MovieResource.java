package io.chillplus.rest;

import io.chillplus.domain.Movie;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

public class MovieResource {

    private List<Movie> movies = new ArrayList<>();

    public Response create(Movie movie) {
        return null;
    }

    public List<Movie> getAll() {
        return null;
    }

    public Movie getOne(String title) {
        return null;
    }

    public Response deleteAll() {
        return null;
    }

}
