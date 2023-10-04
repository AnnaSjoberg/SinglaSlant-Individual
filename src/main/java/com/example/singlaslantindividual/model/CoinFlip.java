package com.example.singlaslantindividual.model;

import org.springframework.stereotype.Component;

/**
 * Class for keeping track of User's and Computer's score, and number of played rounds
 */

@Component
public class CoinFlip {
    private int userScore = 0;
    private int computerScore = 0;
    private int rounds = 0;

    public int getUserScore() {
        return userScore;
    }

    public void setUserScore(int userScore) {
        this.userScore = userScore;
    }

    public int getComputerScore() {
        return computerScore;
    }

    public void setComputerScore(int computerScore) {
        this.computerScore = computerScore;
    }

    public int getRounds() {
        return rounds;
    }

    public void setRounds(int rounds) {
        this.rounds = rounds;
    }
}
