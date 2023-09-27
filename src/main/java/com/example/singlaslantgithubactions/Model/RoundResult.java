package com.example.singlaslantgithubactions.Model;

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


