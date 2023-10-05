package com.example.singlaslantindividual.controllers;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.singlaslantindividual.model.CoinFlip;
import com.example.singlaslantindividual.services.Game;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.jsoup.Jsoup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
@AutoConfigureMockMvc
@Tag("SystemTest")
public class CoinFlipControllerApiTest {

    private Game game;
    @Autowired
    private CoinFlipController controller;

    @BeforeEach
    public void setup() {
        game = new Game(new CoinFlip());
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
    public void onOkPublishFlipEndpointShouldContainTryAgainWhenChoiceIsInvalid() {
        given()
                .param("choice", "tummy")
                .when()
                .post("/flip")
                .then()
                .statusCode(200)
                .body(containsString("try again!"));
    }

    @Test
    public void getFlipEndpointWithoutChoiceReturns405() {
        given()
                .when()
                .get("/flip")
                .then()
                .statusCode(405);
    }

    @Test
    public void postFlipEndpointWithoutChoiceReturns400() {
        given()
                .when()
                .post("/flip")
                .then()
                .statusCode(400);
    }

    @Test
    public void testHighNumberOfRounds() {
        // Get the initial number of turns from the current endpoint
        int startingTurns = getTurnsValue();

        // Simulate a very high number of rounds. Limit to 1000 for capacity reasons
        int numberOfRounds = 1000;
        playRounds(numberOfRounds);
        int expectedTurns = startingTurns + numberOfRounds;
        int actualTurns = getTurnsValue();

        // Verify that the number of turns has increased accordingly
        assertEquals(expectedTurns, actualTurns);
    }

    private int getTurnsValue() {
        String responseBody = given()
                .when()
                .get("/")
                .then()
                .statusCode(200)
                .extract().response().getBody().asString();

        String turnsValue = parseTurnsValue(responseBody);
        return Integer.parseInt(turnsValue);
    }

    private void playRounds(int numberOfRounds) {
        for (int i = 0; i < numberOfRounds; i++) {
            String choice = (i % 2 == 0) ? "heads" : "tails";
            simulateRound(choice);
        }
    }

    private String parseTurnsValue(String responseBody) {
        return Jsoup.parse(responseBody)
                .select("#turnsValue").text();
    }

    private void simulateRound(String choice) {
        given()
                .param("choice", choice)
                .when()
                .post("/flip")
                .then()
                .statusCode(200);
    }

}
