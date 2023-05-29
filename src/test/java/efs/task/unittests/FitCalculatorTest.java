package efs.task.unittests;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import org.junit.jupiter.api.Assertions;

class FitCalculatorTest {
    @Test
    void shouldReturnTrue_whenDietRecommended() {
        // given
        double weight = 89.2;
        double height = 1.72;

        // when
        boolean recommended = FitCalculator.isBMICorrect(weight, height);

        // then
        Assertions.assertTrue(recommended);
    }
/*
    @Test
    void shouldReturnFalse_whenDietRecommended() {
        // given
        double weight = 69.5;
        double height = 1.72;

        // when
        boolean recommended = FitCalculator.isBMICorrect(weight, height);

        // then
        assertFalse(recommended);
    }
*/
    @Test
    void shouldThrowIllegalArgumentException_whenHeightIsZero() {
        // given
        double weight = 80.0;
        double height = 0.0;

        // then
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            // when
            FitCalculator.isBMICorrect(weight, height);
        });
    }

    @ParameterizedTest(name = "weight: {0}")
    @ValueSource(doubles = {92.0, 102.0, 99.2})
    void shouldReturnTrue_whenDietIsRecommended(double weight) {
        //given
        double height = 1.8;
        
        //when
        boolean recommended = FitCalculator.isBMICorrect(weight, height);
        
        //then
        Assertions.assertTrue(recommended);
    }

    @ParameterizedTest(name = "height: {0}, weight: {1}")
    @CsvSource({
            "1.70, 70.0",
            "1.80, 80.5",
            "1.60, 65.2"
    })
    void shouldReturnFalse_whenDietIsNotRecommended(double height, double weight) {
        // when
        boolean recommended = FitCalculator.isBMICorrect(weight, height);

        // then
        Assertions.assertFalse(recommended);
    }

    @ParameterizedTest(name = "height: {0}, weight: {1}")
    @CsvFileSource(resources = {"/data.csv"}, numLinesToSkip = 1)
    void shouldReturnFalse_whenDietIsRecommendedFromCsv(double height, double weight) {
        // when
        boolean recommended = FitCalculator.isBMICorrect(weight, height);

        // then
        Assertions.assertFalse(recommended);
    }

    @Test
    void shouldReturnUserWithWorstBMI() {
        // given
        List<User> userList = TestConstants.TEST_USERS_LIST;
        double weight = 97.3;
        double height = 1.79;
        
        // when
        User userWithWorstBMI = FitCalculator.findUserWithTheWorstBMI(userList);

        // then
        Assertions.assertEquals(weight, userWithWorstBMI.getWeight());
        Assertions.assertEquals(height, userWithWorstBMI.getHeight());
    }

    @Test
    void shouldReturnNull() {
        // given
        List<User> userList = new ArrayList<>();

        // when
        User userWithWorstBMI = FitCalculator.findUserWithTheWorstBMI(userList);

        // then
        Assertions.assertNull(userWithWorstBMI);
    }

    @Test
    void shouldReturnEqualToList() {
        // given
        List<User> userList = TestConstants.TEST_USERS_LIST;

        // when
       double[] bmiScores = FitCalculator.calculateBMIScore(userList);

        // then
        double[] expectedBmiScores = TestConstants.TEST_USERS_BMI_SCORE;
        Assertions.assertArrayEquals(expectedBmiScores, bmiScores);
    }
}
