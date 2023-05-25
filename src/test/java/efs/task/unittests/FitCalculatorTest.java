package efs.task.unittests;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.CsvFileSource;

class FitCalculatorTest {

    @Test
    void shouldReturnTrue_whenDietRecommended() {
        //given
        double weight = 89.2;
        double height = 1.72;

        //when
        boolean recommended = FitCalculator.isBMICorrect(weight, height);

        //then
        assertTrue(recommended);
    }
    
    @Test
    void shouldReturnFalse_whenBMICorrectReturnsFalse() {
        //given
        double weight = 69.5;
        double height = 1.72;

        //when
        boolean bmiCorrect = FitCalculator.isBMICorrect(weight, height);

        //then
        assertFalse(bmiCorrect);
    }
    
    @Test
    void shouldThrowIllegalArgumentException_whenHeightIsZero() {
        //given
        double weight = 80.0;
        double height = 0.0;

        //then
        assertThrows(IllegalArgumentException.class, () -> {
            //when
            FitCalculator.isBMICorrect(weight, height);
        });
    }
    
    @ParameterizedTest(name = "Weight={0}")
    @ValueSource(doubles = {60.0, 70.0, 80.0}) // Test cases for different weights
    void shouldReturnTrue_forAllWeights_whenFixedHeight(double weight) {
        // given
        double height = 1.80;

        // when
        boolean bmiCorrect = FitCalculator.isBMICorrect(weight, height);

        // then
        assertTrue(bmiCorrect);
    }
    
    @ParameterizedTest(name = "Height={0}, Weight={1}")
    @CsvSource({
            "1.70, 70.0",
            "1.80, 80.5",
            "1.60, 65.2"
    }) // Test cases for height and weight pairs
    void shouldReturnFalse_forAllPairs_whenHeightAndWeightProvided(double height, double weight) {
        // when
        boolean bmiCorrect = FitCalculator.isBMICorrect(weight, height);

        // then
        assertFalse(bmiCorrect);
    }
    
    @ParameterizedTest(name = "Height={0}, Weight={1}")
    @CsvFileSource(resources = "/data.csv") // CSV file containing height and weight pairs
    void shouldReturnFalse_forAllPairsFromFile_whenHeightAndWeightFromCSV(double height, double weight) {
        // when
        boolean bmiCorrect = FitCalculator.isBMICorrect(weight, height);

        // then
        assertFalse(bmiCorrect);
    }
}
