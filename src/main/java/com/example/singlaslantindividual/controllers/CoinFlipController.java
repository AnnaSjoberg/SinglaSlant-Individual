package com.example.singlaslantindividual.controllers;

import com.example.singlaslantindividual.jacocoutil.Generated;
import com.example.singlaslantindividual.model.CoinFlip;
import com.example.singlaslantindividual.model.RoundResult;
import com.example.singlaslantindividual.services.Game;
import com.example.singlaslantindividual.services.RandomNumberGenerator;
import com.example.singlaslantindividual.services.WinRateCalculator;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Class that contols the endpoints for the game
 * Holds instantiated objects of classes
 * - Game
 * - WinRateCalculator
 * - RoundResult
 * - RandomNumberGenerator
 * <p>
 * Is excluded from the JaCoCo test report
 */

@Controller
@Generated
public class CoinFlipController {

    private List<RoundResult> rounds = new ArrayList<>();
    private final Game game;
    private final WinRateCalculator winRateCalculator;
    private final RandomNumberGenerator randomNumberGenerator;

    @Autowired
    public CoinFlipController(Game game, WinRateCalculator winRateCalculator,
                              RandomNumberGenerator randomNumberGenerator) {
        this.game = game;
        this.winRateCalculator = winRateCalculator;
        this.randomNumberGenerator = randomNumberGenerator;
    }

    //Startsida där användaren väljer heads/tails
    @GetMapping("/")
    public String index(Model model) {
        CoinFlip coinFlip = game.getCoinFlip();
        double winRate = winRateCalculator.calculateWinRate(coinFlip);

        model.addAttribute("userScore", coinFlip.getUserScore());
        model.addAttribute("computerScore", coinFlip.getComputerScore());
        model.addAttribute("turns", coinFlip.getTurns());
        model.addAttribute("winRate", winRate);
        return "index";
    }

    //När användaren valt heads/tails uppdateras sidan med relevant information i /flip-endpointen
    //Användaren fortsätter spela så många omgångar hen vill
    @PostMapping("/flip")
    public String publishFlip(@RequestParam String choice, Model model) {
        double resultAsDouble = randomNumberGenerator.generateRandomNumber();
        RoundResult roundResult = game.playGame(choice, resultAsDouble);
        double winRate = winRateCalculator.calculateWinRate(game.getCoinFlip());
        String result = "";
        if (roundResult == null) {
            result = choice + " is not a valid choice. Please try again!";
        } else {
            result = roundResult.getWinner() + " WINS!!!!!";
            rounds.add(roundResult);
        }
        model.addAttribute("rounds", rounds);
        model.addAttribute("Result", result);
        model.addAttribute("userScore", game.getCoinFlip().getUserScore());
        model.addAttribute("computerScore", game.getCoinFlip().getComputerScore());
        model.addAttribute("turns", game.getCoinFlip().getTurns());
        model.addAttribute("winRate", winRate);

        return "index";
    }
}
