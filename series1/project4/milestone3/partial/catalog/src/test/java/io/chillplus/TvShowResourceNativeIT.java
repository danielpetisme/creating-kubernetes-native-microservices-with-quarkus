package io.chillplus;

import io.chillplus.domain.TvShow;
import io.quarkus.test.junit.DisabledOnIntegrationTest;
import io.quarkus.test.junit.QuarkusIntegrationTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.hamcrest.Matchers.is;

@QuarkusIntegrationTest
public class TvShowResourceNativeIT extends TvShowResourceTest {

    @Test
    public void getAllTvShowsNative() {
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

        given()
                .when()
                .get("/api/tv")
                .then()
                .statusCode(200)
                .contentType(APPLICATION_JSON)
                .body("$.size()", is(2))
                .extract().jsonPath().getList("", TvShow.class);
    }

    @DisabledOnIntegrationTest
    @Test
    public void checkTvShowTitleIsNotBlank() {
    }

}