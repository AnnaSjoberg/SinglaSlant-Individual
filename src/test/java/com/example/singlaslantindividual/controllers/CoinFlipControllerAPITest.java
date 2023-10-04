package com.example.singlaslantindividual.controllers;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

import com.example.singlaslantindividual.services.Game;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;


@SpringBootTest
@AutoConfigureMockMvc
public class CoinFlipControllerAPITest {

    @MockBean
    private Game game;
    @Autowired
    private CoinFlipController controller;

    @BeforeEach
    public void setup() {
        RestAssuredMockMvc.standaloneSetup(controller);
    }


    @Test
    public void indexEndpointShouldContainStartingPointStatsOnOk() {
        given()
                .when()
                .get("/")
                .then()
                .statusCode(200)
                .body(containsString("User Score:"))
                .body(containsString("Computer Score:"))
                .body(containsString("Turns:"))
                .body(containsString("Win Rate:"));
    }

    @Test
    public void onOkPublishFlipEndpointShouldContainWinsWhenChoiceIsValid() {
        given()
                .param("choice", "heads") // Set the request parameter as needed
                .when()
                .post("/flip")
                .then()
                .statusCode(200)
                .body(containsString("WINS!!!!!"));
    }
    @Test
    public void onOkPublishFlipEndpointShouldContainNotAllowedWhenChoiceIsInvalid() {
        given()
                .param("choice", "tummy") // Set the request parameter as needed
                .when()
                .post("/flip")
                .then()
                .statusCode(200)
                .body(containsString("not allowed"));
    }

    @Test
    public void flipEndpointWithoutChoiceReturns405() {
        given()
                .when()
                .get("/flip")
                .then()
                .statusCode(405);
    }

}
