package com.football;

import com.football.config.FootballConfig;
import com.football.pojo.Area;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.List;
import java.util.Optional;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;

/**
 * Endpoints tested:
 * /areas
 * /areas/{id}
 */

public class AllAreasApiTests extends FootballConfig {

    @Test
    @DisplayName("Get all areas")
    public void getAllAreas() {
        int expectedAreas = 272;
        List<String> listOfAreasIds =
                given()
                        .when()
                        .get(Endpoints.ALL_AREAS)
                        .jsonPath()
                        .getList("areas.id");

        assertEquals(expectedAreas, listOfAreasIds.size());
    }

    @Test
    @DisplayName("Get all areas with Gpath")
    public void getAllAreasWithGpath() {
        int expectedAreas = 272;

        Response response = given()
                .when()
                .get(Endpoints.ALL_AREAS)
                .then()
                .extract().response();

        List<String> listOfAreasIds = response.path("areas.findAll { it.id }.id");

        assertEquals(expectedAreas, listOfAreasIds.size());
    }

    @Test
    @DisplayName("Get the highest area id")
    public void getTheHighestAreaId() {
        int expectedHighestId = 2272;

        Response response = given()
                .when()
                .get(Endpoints.ALL_AREAS)
                .then()
                .extract().response();

        int actualHighestId = response.path("areas.max { it.id }.id");
        assertEquals(expectedHighestId, actualHighestId);
    }

    @Test
    @DisplayName("Get all countries from europe ")
    public void getAllCountriesFromEurope() {
        int expectedCountriesNumber = 74;

        Response response = given()
                .when()
                .get(Endpoints.ALL_AREAS)
                .then()
                .extract().response();

        List<String> europeanCountries = response.path("areas.findAll { it.parentArea == 'Europe' }");
        int actualCountriesNumber = europeanCountries.size();

        assertEquals(expectedCountriesNumber, actualCountriesNumber);
    }

    @Test
    @DisplayName("Test status code of 200")
    public void testStatusCodeOf200() {
        Response response = given()
                .when()
                .get(Endpoints.ALL_AREAS)
                .then()
                .extract().response();

        assertEquals(200, response.getStatusCode());
    }

    @Test
    @DisplayName("Test response time of less than 3ms")
    public void testResponseTimeOfLessThan3Ms() {
        given()
                .when()
                .get(Endpoints.ALL_AREAS)
                .then()
                .time(lessThan(3000L));
    }
    
    /* 
    /areas/{id}
     */

    @Test
    @DisplayName("Get Zimbabwe details deserialization")
    public void getZimbabweDetailsDeserialization() {
        Response response =
                given()
                        .pathParam("id", 2272)
                        .when()
                        .get(Endpoints.SINGLE_AREA)
                        .then()
                        .extract().response();

        Area zimbabwe = response.getBody().as(Area.class);
        int expectedId = 2272;
        String expectedName = "Zimbabwe";
        String expectedCode = "ZIM";
        int expectedParentAreaId = 2001;
        String expectedParentArea = "Africa";

        assertAll(
                () -> assertEquals(Optional.of(expectedId).get(), zimbabwe.getId()),
                () -> assertEquals(expectedName, zimbabwe.getName()),
                () -> assertEquals(expectedCode, zimbabwe.getCode()),
                () -> assertEquals(Optional.of(expectedParentAreaId).get(), zimbabwe.getParentAreaId()),
                () -> assertEquals(expectedParentArea, zimbabwe.getParentArea())
        );
    }

    @Test
    @DisplayName("Test status code of single area 200")
    public void testStatusCodeOfSingleArea200() {
        given()
                .pathParam("id", 2272)
                .when()
                .get(Endpoints.SINGLE_AREA)
                .then()
                .statusCode(200);
    }

    @Test
    @DisplayName("Test status code of a wrong area 404")
    public void testStatusCodeOfAWrongArea() {
        given()
                .pathParam("id", 1598)
                .when()
                .get(Endpoints.SINGLE_AREA)
                .then()
                .statusCode(404);
    }

    @Test
    @DisplayName("Test response message of a wrong input")
    public void testResponseMessageOfAWrongInput() {
        Response response = given()
                .pathParam("id", 1598)
                .when()
                .get(Endpoints.SINGLE_AREA)
                .then()
                .extract().response();

        String expectedMessage = "The resource you are looking for does not exist.";
        assertEquals(expectedMessage, response.jsonPath().getString("message"));
    }

    @Test
    @DisplayName("Test Api version should be 4")
    public void testApiVersionShouldBe4() {
        Response response = given()
                .pathParam("id", 2272)
                .when()
                .get(Endpoints.SINGLE_AREA)
                .then()
                .extract().response();

        String expectedAPIVersion = "v4";
        String actualAPIVersion = response.header("X-API-Version");

        assertEquals(expectedAPIVersion, actualAPIVersion);
    }


}
