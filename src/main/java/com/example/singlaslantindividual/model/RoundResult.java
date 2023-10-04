package com.example.singlaslantindividual.model;

/**
 * Class that stores user's choice (heads/tails) along with the winner of the current round.
 * Is used in both Game for gaming logic, and in the Coinflip'controller as an instantiated object
 */

public class RoundResult {
    private String userChoice;
    private String computerChoice;
    private String winner;

    public RoundResult(String userChoice, String computerChoice, String winner) {
        this.userChoice = userChoice;
        this.computerChoice = computerChoice;
        this.winner = winner;
    }

    public String getUserChoice() {
        return userChoice;
    }

    public void setUserChoice(String userChoice) {
        this.userChoice = userChoice;
    }

    public String getComputerChoice() {
        return computerChoice;
    }

    public void setComputerChoice(String computerChoice) {
        this.computerChoice = computerChoice;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }
}


