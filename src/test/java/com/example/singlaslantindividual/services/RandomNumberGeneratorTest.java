package com.example.singlaslantindividual.services;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RandomNumberGeneratorTest {
    RandomNumberGenerator randomNumberGenerator;

    @BeforeEach
    void setup() {
        randomNumberGenerator = new RandomNumberGenerator();
    }

    @Test
    void generateRandomNumberShouldReturnAtLeast0() {
        assertTrue(randomNumberGenerator.generateRandomNumber() >= 0);
    }

    @Test
    void generateRandomNumberShouldReturnLessThan1() {
        assertTrue(randomNumberGenerator.generateRandomNumber() < 1);
    }

}