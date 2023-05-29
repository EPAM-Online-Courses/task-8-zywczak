package efs.task.unittests;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class FitCalculatorTest {
    FitCalculatorTest() {
    }

    @Test
    void shouldReturnTrue_whenDietRecommended() {
        double weight = 89.2;
        double height = 1.72;
        boolean recommended = FitCalculator.isBMICorrect(weight, height);
        Assertions.assertTrue(recommended);
    }

    @Test
    void shouldThrowIllegalArgumentException_whenHeightIsZero() {
        double weight = 89.2;
        double height = 0.0;
        Class expectedException = IllegalArgumentException.class;
        Assertions.assertThrows(expectedException, () -> FitCalculator.isBMICorrect(weight, height));
    }

    @ParameterizedTest(
            name = "weight: {0}"
    )
    @ValueSource(
            doubles = {92.0, 102.0, 99.2}
    )
    void shouldReturnTrue_whenDietIsRecommended(double weight) {
        double height = 1.8;
        boolean recommended = FitCalculator.isBMICorrect(weight, height);
        Assertions.assertTrue(recommended);
    }

    @ParameterizedTest(
            name = "weight: {0}, height: {1}"
    )
    @CsvSource({"1.80, 72", "1.80, 63.0", "1.80, 79.2"})
    void shouldReturnFalse_whenDietIsNotRecommended(double height, double weight) {
        boolean recommended = FitCalculator.isBMICorrect(weight, height);
        Assertions.assertFalse(recommended);
    }

    @ParameterizedTest(
            name = "weight: {0}, height: {1}"
    )
    @CsvFileSource(
            resources = {"/data.csv"},
            numLinesToSkip = 1
    )
    void shouldReturnFalse_whenDietIsRecommendedFromFile(double weight, double height) {
        boolean recommended = FitCalculator.isBMICorrect(weight, height);
        Assertions.assertFalse(recommended);
    }

    @Test
    void shouldReturnUserWithTheWorstBMI() {
        List<User> testConstants = TestConstants.TEST_USERS_LIST;
        double weight = 97.3;
        double height = 1.79;
        User userWithTheWorstBMI = FitCalculator.findUserWithTheWorstBMI(testConstants);
        Assertions.assertEquals(weight, userWithTheWorstBMI.getWeight());
        Assertions.assertEquals(height, userWithTheWorstBMI.getHeight());
    }

    @Test
    void shouldReturnNull() {
        List<User> testConstants = new ArrayList<>();
        User userWithTheWorstBMI = FitCalculator.findUserWithTheWorstBMI(testConstants);
        Assertions.assertNull(userWithTheWorstBMI);
    }

    @Test
    void shouldBeEqualToGivenList() {
        List<User> testConstants = TestConstants.TEST_USERS_LIST;
        double[] expectedBmiScore = TestConstants.TEST_USERS_BMI_SCORE;
        double[] bmiScore = FitCalculator.calculateBMIScore(testConstants);
        Assertions.assertArrayEquals(expectedBmiScore, bmiScore);
    }
}
