package com.example.singlaslantindividual.services;

import org.springframework.stereotype.Component;

/**
 *Class for generating a random number 0-1
 */

@Component
public class RandomNumberGenerator {

    public double generateRandomNumber() {
        return Math.random();
    }
}
