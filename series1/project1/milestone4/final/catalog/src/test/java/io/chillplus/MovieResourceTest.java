package io.chillplus;

import io.chillplus.domain.Movie;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.hamcrest.Matchers.is;

@QuarkusTest
public class MovieResourceTest {

    public static final String DEFAULT_TITLE = "AAA";

    @BeforeEach
    public void beforeEach() {
        given()
                .when()
                .delete("/api/movies")
                .then()
                .statusCode(200);
    }

    @Test
    public void should_create_movie() {
        given()
                .when()
                .get("/api/movies")
                .then()
                .statusCode(200)
                .contentType(APPLICATION_JSON)
                .body("$.size()", is(0));

        Movie movie = new Movie();
        movie.title = DEFAULT_TITLE;

        given() // An empty movie list
                .body(movie)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .when()
                .post("/api/movies")
                .then() // Get a successful created response with the movie
                .statusCode(201)
                .contentType(APPLICATION_JSON)
                .body("title", is(movie.title));

        given()
                .when()
                .get("/api/movies")
                .then() // Get a successful JSON response with an array of 1 movie
                .statusCode(200)
                .contentType(APPLICATION_JSON)
                .body("$.size()", is(1));
    }


    @Test
    public void should_not_create_movie_with_blank_title() {
        given()
                .when()
                .get("/api/movies")
                .then()
                .statusCode(200)
                .contentType(APPLICATION_JSON)
                .body("$.size()", is(0));

        Movie movie = new Movie();
        movie.title = "";

        given() // An empty movie list
                .body(movie)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .when()
                .post("/api/movies")
                .then() // Get an client error
                .statusCode(400);

        given()
                .when()
                .get("/api/movies")
                .then() // Get a successful JSON response with an array of 1 movie
                .statusCode(200)
                .contentType(APPLICATION_JSON)
                .body("$.size()", is(0));
    }

    @Test
    public void should_get_all_movies() {
        given() // An empty movie list
                .when()
                .get("/api/movies")
                .then() // Get a successful JSON response with an empty array
                .statusCode(200)
                .contentType(APPLICATION_JSON)
                .body("$.size()", is(0));

        Movie movie = new Movie();
        movie.title = DEFAULT_TITLE;

        given() // An empty movie list
                .body(movie)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .when()
                .post("/api/movies")
                .then() // Get a successful created response with the movie
                .statusCode(201)
                .contentType(APPLICATION_JSON)
                .body("title", is(movie.title));

        given() // An movie list containing 1 movie
                .when()
                .get("/api/movies")
                .then() // Get a successful JSON response with an array of 1 movie
                .statusCode(200)
                .contentType(APPLICATION_JSON)
                .body("$.size()", is(1));
    }

    @Test
    public void should_get_one_movie() {
        Movie movie = new Movie();
        movie.title = DEFAULT_TITLE;

        given() // An empty movie list
                .body(movie)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .when()
                .post("/api/movies")
                .then() // Get a successful created response with the movie
                .statusCode(201)
                .contentType(APPLICATION_JSON)
                .body("title", is(movie.title));


        given()// An movie list containing 1 movie
                .when()
                .get("/api/movies/{title}", DEFAULT_TITLE)
                .then() // Get a successful response with the correct movie
                .statusCode(200)
                .contentType(APPLICATION_JSON)
                .body("title", is(movie.title));
    }

    @Test
    public void should_get_404_when_unknown_movie() {
        given() // An empty movie list
                .when()
                .get("/api/movies/unknown")
                .then() // Get an error with the 404 status code
                .statusCode(404);
    }

    @Test
    public void shoud_delete_all_movies() {
        Movie movie = new Movie();
        movie.title = DEFAULT_TITLE;

        given() // An empty movie list
                .body(movie)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .when()
                .post("/api/movies")
                .then() // Get a successful created response with the movie
                .statusCode(201)
                .contentType(APPLICATION_JSON)
                .body("title", is(movie.title));

        given() // An movie list containing 1 movie
                .when()
                .get("/api/movies")
                .then() // Get a successful JSON response with an array of 1 movie
                .statusCode(200)
                .contentType(APPLICATION_JSON)
                .body("$.size()", is(1));

        given()
                .when()
                .delete("/api/movies")
                .then()
                .statusCode(200);

        given()
                .when()
                .get("/api/movies")
                .then() // Get a successful JSON response with an array of 0 movies
                .statusCode(200)
                .contentType(APPLICATION_JSON)
                .body("$.size()", is(0));
    }

}