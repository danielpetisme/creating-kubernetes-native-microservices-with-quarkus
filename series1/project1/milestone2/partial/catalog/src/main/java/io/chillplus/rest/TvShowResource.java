package io.chillplus.rest;

import io.chillplus.domain.TvShow;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

public class TvShowResource {

    private long sequence = 0;
    private List<TvShow> tvShows = new ArrayList<>();

    public Response create(TvShow tvShow) {
        return null;
    }

    public List<TvShow> getAll() {
        return null;
    }

    public TvShow getOneById(long id) {
        return null;
    }

    public Response deleteAll() {
        return null;
    }

    public Response deleteOne(long id) {
        return null;
    }

}
