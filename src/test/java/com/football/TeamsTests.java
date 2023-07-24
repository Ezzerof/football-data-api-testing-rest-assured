package com.football;

import com.football.config.FootballConfig;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertLinesMatch;

/**
     * Endpoints tested:
     * /teams
     * /teams/{id}
     */
public class TeamsTests extends FootballConfig {


    @Test
    @DisplayName("Count teams should be 50")
    public void countTeams() {
        Response response = given()
                .when()
                .get(Endpoints.ALL_TEAMS)
                .then()
                .extract().response();

        List<String> allTeamsDetails = response.path("teams");
        int expectedNumberOfTeams = response.path("count");
        int actualNumberOfTeams = allTeamsDetails.size();
        assertEquals(expectedNumberOfTeams, actualNumberOfTeams);
    }

    @Test
    @DisplayName("Check first team name should be FC Koln")
    public void checkFirstTeamNameShouldBeFcKoln() {
        String firstTeam = given()
                .when()
                .get(Endpoints.ALL_TEAMS)
                .jsonPath()
                .getString("teams.name[0]");

        String expectedTeamName = "1. FC Köln";
        assertEquals(firstTeam, expectedTeamName);
    }

    @Test
    @DisplayName("Check first 2 and last 2 team names")
    public void checkFirst2AndLast2TeamNames() {
        Response response = given()
                .when()
                .get(Endpoints.ALL_TEAMS)
                .then()
                .extract().response();

        List<String> allTeamNames = response.path("teams.name");
        List<String> actualTeamNames = new ArrayList<>(
                Arrays.asList(allTeamNames.get(0),
                        allTeamNames.get(1),
                        allTeamNames.get(allTeamNames.size() - 2),
                        allTeamNames.get(allTeamNames.size() - 1))
        );

        List<String> expectedTeamNames = new ArrayList<>(Arrays.asList("1. FC Köln", "TSG 1899 Hoffenheim","VfR Aalen" , "SV Wacker Burghausen"));
        assertLinesMatch(actualTeamNames, expectedTeamNames);
    }

    @Test
    @DisplayName("Get all teams with club colors red and white")
    public void getAllTeamsWithClubColorsWhiteAndBlack() {
        Response response = given()
                .when()
                .get(Endpoints.ALL_TEAMS)
                .then()
                .extract().response();

        List<String> clubsNamesWithColorRedAndWhite = response.path("teams.findAll { it.clubColors == 'Red / White' }.name");
        List<String> expectedClubsNamesWithRedAndWhiteColors = new ArrayList<>(Arrays.asList(
                "1. FC Köln",
                "1. FC Kaiserslautern",
                "1. FC Nürnberg",
                "1. FSV Mainz 05",
                "SC Freiburg",
                "FC Energie Cottbus",
                "TSV Fortuna 95 Düsseldorf",
                "1. FC Union Berlin",
                "SC Rot-Weiß Oberhausen",
                "SSV Jahn Regensburg"
        ));

        assertLinesMatch(expectedClubsNamesWithRedAndWhiteColors, clubsNamesWithColorRedAndWhite);
    }

    /*
    /teams/{id}
     */

    @Test
    @DisplayName("Get team with id 59")
    public void getTeamWithId29() {
        int teamId = 59;
        String expectedTeamName = "Blackburn Rovers FC";

        String actualTeamName = given()
                .pathParam("id", teamId)
                .when()
                .get(Endpoints.SINGLE_TEAM)
                .jsonPath()
                .getString("name");

        assertEquals(expectedTeamName, actualTeamName);

    }

    @Test
    @DisplayName("Count squad of Blackburn Rovers")
    public void countSquadOfBlackburnRovers() {
        int expectedSquadNumber = 35;

        Response response = given()
                .pathParam("id", 59)
                .when()
                .get(Endpoints.SINGLE_TEAM)
                .then()
                .extract().response();

        List<String> squad = response.path("squad");
        int actualSquadNumber = squad.size();

        assertEquals(expectedSquadNumber, actualSquadNumber);
    }

    @Test
    @DisplayName("Find all players from England")
    public void findAllPlayersFromEngland() {
        int expectedPlayersNumber = 26;

        Response response = given()
                .pathParam("id", 59)
                .when()
                .get(Endpoints.SINGLE_TEAM)
                .then()
                .extract().response();

        List<String> playersFromEngland = response.path("squad.findAll { it.nationality == 'England' }");
        int actualPlayersNumber = playersFromEngland.size();

        assertEquals(expectedPlayersNumber, actualPlayersNumber);
    }

}
