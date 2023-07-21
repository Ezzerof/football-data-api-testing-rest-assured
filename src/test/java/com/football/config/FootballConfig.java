package com.football.config;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.BeforeClass;

public class FootballConfig {

    @BeforeClass
    public static void setup() {
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setBaseUri("https://api.football-data.org")
                .setBasePath("/v4")
                .setContentType("application/json")
                .setAccept("application/json")
                .addHeader("X-Auth-Token", "3fc7a62973fd4bbd9af7b3c159e95f59")
                .addFilter(new ResponseLoggingFilter())
                .build();

        RestAssured.responseSpecification = new ResponseSpecBuilder()
//                .expectStatusCode(200)
                .build();
    }

}
