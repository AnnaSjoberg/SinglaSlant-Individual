package com.example.singlaslantindividual.services;

import com.example.singlaslantindividual.model.CoinFlip;
import com.example.singlaslantindividual.model.RoundResult;
import org.springframework.stereotype.Component;

/**
 * A class that manages the game, using a CoinFlip instance to track user and computer scores,
 * as well as the number of rounds played.
 * <p>
 * The Game class handles the logic for:
 * - Selecting 'heads' or 'tails.'
 * - Setting the computer's choice of 'heads' or 'tails' based on user input.
 * - Determining the winner between the user and computer.
 * - Updating the CoinFlip instance's variables with the current values.
 */

@Component
public class Game {
    private final CoinFlip coinFlip;

    public Game(CoinFlip coinFlip) {
        this.coinFlip = coinFlip;
    }

    public RoundResult playGame(String choice, double resultAsDouble) {
        String result = flipCoin(resultAsDouble);
        if (validateChoice(choice)) {
            String computerChoice = calculateComputerChoice(choice);
            String winner = calculateWinner(choice, result);
            updateCoinFlip(winner);

            return new RoundResult(choice, computerChoice, winner);
        }
        return null;
    }

    protected String flipCoin(double randomNumber) {
        return randomNumber > 0.5 ? "heads" : "tails";
    }

    protected String calculateComputerChoice(String choice) {
        return choice.equals("heads") ? "tails" : "heads";
    }

    protected String calculateWinner(String choice, String result) {
        if (choice.equals(result)) {
            return "User";
        } else {
            return "Computer";
        }
    }

    protected void updateCoinFlip(String winner) {
        switch (winner) {
            case "User":
                coinFlip.setUserScore(coinFlip.getUserScore() + 1);
                break;
            case "Computer":
                coinFlip.setComputerScore(coinFlip.getComputerScore() + 1);
                break;
            default:
                return;
        }
        coinFlip.setTurns(coinFlip.getTurns() + 1);
    }

    public CoinFlip getCoinFlip() {
        return coinFlip;
    }

    public boolean validateChoice(String choice) {
        return (choice.equals("heads") || choice.equals("tails"));
    }
}
