package io.chillplus;

import io.chillplus.domain.TvShow;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@QuarkusTest
public class TvShowResourceTest {

    public static final String DEFAULT_TITLE = "AAA";

    @BeforeEach
    public void beforeEach() {
        given()
                .when()
                .delete("/api/tv")
                .then()
                .statusCode(200);
    }

    @Test
    public void createTvShow() {
        given()
                .when()
                .get("/api/tv")
                .then()
                .statusCode(200)
                .contentType(APPLICATION_JSON)
                .body("$.size()", is(0));

        TvShow tvShow = new TvShow();
        tvShow.title = DEFAULT_TITLE;

        given()
                .body(tvShow)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .when()
                .post("/api/tv")
                .then()
                .statusCode(201)
                .contentType(APPLICATION_JSON)
                .body("title", is(tvShow.title));

        given()
                .when()
                .get("/api/tv")
                .then()
                .statusCode(200)
                .contentType(APPLICATION_JSON)
                .body("$.size()", is(1));

        TvShow tvShowWithId = new TvShow();
        tvShow.id = 1L;
        tvShow.title = DEFAULT_TITLE;

        given()
                .body(tvShowWithId)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .when()
                .post("/api/tv")
                .then()
                .statusCode(400);
    }

    @Test
    public void checkTvShowTitleIsNotBlank() {
        given()
                .when()
                .get("/api/tv")
                .then()
                .statusCode(200)
                .contentType(APPLICATION_JSON)
                .body("$.size()", is(0));

        TvShow tvShow = new TvShow();
        tvShow.title = "";

        given()
                .body(tvShow)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .when()
                .post("/api/tv")
                .then()
                .statusCode(400);

        given()
                .when()
                .get("/api/tv")
                .then()
                .statusCode(200)
                .contentType(APPLICATION_JSON)
                .body("$.size()", is(0));
    }

    @Test
    public void updateTvShow() {
        given()
                .when()
                .get("/api/tv")
                .then()
                .statusCode(200)
                .contentType(APPLICATION_JSON)
                .body("$.size()", is(0));

        TvShow tvShow = new TvShow();
        tvShow.title = DEFAULT_TITLE;

        TvShow tvShowPersisted = given()
                .body(tvShow)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .when()
                .post("/api/tv")
                .then()
                .statusCode(201)
                .contentType(APPLICATION_JSON)
                .body("title", is(tvShow.title))
                .extract().as(TvShow.class);

        given()
                .when()
                .get("/api/tv")
                .then()
                .statusCode(200)
                .contentType(APPLICATION_JSON)
                .body("$.size()", is(1));

        tvShowPersisted.title = "BB";

        given()
                .body(tvShowPersisted)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .when()
                .put("/api/tv")
                .then()
                .statusCode(200)
                .contentType(APPLICATION_JSON)
                .body("title", is("BB"));

        TvShow tvShowWithoutId = new TvShow();
        tvShow.title = DEFAULT_TITLE;

        given()
                .body(tvShowWithoutId)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .when()
                .put("/api/tv")
                .then()
                .statusCode(400);
    }

    @Test
    public void getAllTvShows() {
        given()
                .when()
                .get("/api/tv")
                .then()
                .statusCode(200)
                .contentType(APPLICATION_JSON)
                .body("$.size()", is(0));

        TvShow bbShow = new TvShow();
        bbShow.title = "AA";

        given()
                .body(bbShow)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .when()
                .post("/api/tv")
                .then()
                .statusCode(201)
                .contentType(APPLICATION_JSON)
                .body("title", is(bbShow.title));

        TvShow aaShow = new TvShow();
        aaShow.title = "BB";

        given()
                .body(aaShow)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .when()
                .post("/api/tv")
                .then()
                .statusCode(201)
                .contentType(APPLICATION_JSON)
                .body("title", is(aaShow.title));

        List<TvShow> result = given()
                .when()
                .get("/api/tv")
                .then()
                .statusCode(200)
                .contentType(APPLICATION_JSON)
                .body("$.size()", is(2))
                .extract().jsonPath().getList("", TvShow.class);
    }

    @Test
    public void getOneTvShow() {
        given()
                .when()
                .get("/api/tv")
                .then()
                .statusCode(200)
                .contentType(APPLICATION_JSON)
                .body("$.size()", is(0));

        TvShow tvShow = new TvShow();
        tvShow.title = DEFAULT_TITLE;

        TvShow result = given()
                .body(tvShow)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .when()
                .post("/api/tv")
                .then()
                .statusCode(201)
                .contentType(APPLICATION_JSON)
                .body("title", is(tvShow.title))
                .extract().as(TvShow.class);


        given()
                .when()
                .get("/api/tv/{id}", result.id)
                .then()
                .statusCode(200);
    }

    @Test
    public void getNonExistingTvShow() {
        given()
                .when()
                .get("/api/tv/0")
                .then()
                .statusCode(404);
    }

    @Test
    public void deleteAllTvShows() {
        TvShow tvShow = new TvShow();
        tvShow.title = DEFAULT_TITLE;

        given()
                .body(tvShow)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .when()
                .post("/api/tv")
                .then()
                .statusCode(201)
                .contentType(APPLICATION_JSON)
                .body("title", is(tvShow.title));

        given()
                .when()
                .get("/api/tv")
                .then()
                .statusCode(200)
                .contentType(APPLICATION_JSON)
                .body("$.size()", is(1));

        given()
                .when()
                .delete("/api/tv")
                .then()
                .statusCode(200);

        given()
                .when()
                .get("/api/tv")
                .then()
                .statusCode(200)
                .contentType(APPLICATION_JSON)
                .body("$.size()", is(0));
    }

    @Test
    public void deleteOneTvShow() {
        TvShow tvShow = new TvShow();
        tvShow.title = DEFAULT_TITLE;

        given()
                .body(tvShow)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .when()
                .post("/api/tv")
                .then()
                .statusCode(201)
                .contentType(APPLICATION_JSON)
                .body("title", is(tvShow.title));

        given()
                .when()
                .get("/api/tv")
                .then()
                .statusCode(200)
                .contentType(APPLICATION_JSON)
                .body("$.size()", is(1));

        given()
                .when()
                .delete("/api/tv")
                .then()
                .statusCode(200);

        given()
                .when()
                .get("/api/tv")
                .then()
                .statusCode(200)
                .contentType(APPLICATION_JSON)
                .body("$.size()", is(0));
    }

}