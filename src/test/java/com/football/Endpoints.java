package com.football;

public interface Endpoints {

    String ALL_AREAS = "/areas";
    String SINGLE_AREA = "/areas/{id}";

    String SINGLE_TEAM = "/teams/{id}";
    String ALL_TEAMS = "/teams";
    String COMPETITIONS_TEAMS = "/competitions/{id}/teams";

}
