package com.example.singlaslantindividual.services;

import com.example.singlaslantindividual.model.CoinFlip;
import org.springframework.stereotype.Component;
/**
 * Calculates the win rate for a given CoinFlip object.
 *
 * Takes in a 'CoinFlip' object for which to calculate the win rate.
 * Returns the win rate as a percentage.
 */

@Component
public class WinRateCalculator {

    public double calculateWinRate(CoinFlip coinFlip) {
        if (coinFlip.getRounds() == 0) {
            return 0.0;
        }
        return ((double) coinFlip.getUserScore() / coinFlip.getRounds()) * 100;
    }
}

