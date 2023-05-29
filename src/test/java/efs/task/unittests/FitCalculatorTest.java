package efs.task.unittests;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class FitCalculatorTest {
    @Test
    void shouldReturnTrue_whenDietRecommended() {
        // given
        double weight = 89.2;
        double height = 1.72;

        // when
        boolean recommended = FitCalculator.isBMICorrect(weight, height);

        // then
        assertTrue(recommended);
    }

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
    
    @Test
    void shouldThrowIllegalArgumentException_whenHeightIsZero() {
        // given
        double weight = 80.0;
        double height = 0.0;

        // then
        assertThrows(IllegalArgumentException.class, () -> {
            // when
            FitCalculator.isBMICorrect(weight, height);
        });
    }

    @ParameterizedTest(name = "weight: {0}")
    @ValueSource(doubles = {90.0, 110.0, 100.0})
    void shouldReturnTrue_whenDietIsRecommended(double weight) {
        //given
        double height = 1.8;
        
        //when
        boolean recommended = FitCalculator.isBMICorrect(weight, height);
        
        //then
        assertTrue(recommended);
    }

    @ParameterizedTest(name = "height: {0}, weight: {1}")
    @CsvSource({
            "1.70, 69.0",
            "1.80, 75.5",
            "1.60, 55.6"
    })
    void shouldReturnFalse_whenDietIsNotRecommended(double height, double weight) {
        // when
        boolean recommended = FitCalculator.isBMICorrect(weight, height);

        // then
        assertFalse(recommended);
    }

    @ParameterizedTest(name = "height: {0}, weight: {1}")
    @CsvFileSource(resources = {"/data.csv"}, numLinesToSkip = 1)
    void shouldReturnFalse_whenDietIsRecommendedFromCsv(double height, double weight) {
        // when
        boolean recommended = FitCalculator.isBMICorrect(weight, height);

        // then
        assertFalse(recommended);
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
        assertEquals(weight, userWithWorstBMI.getWeight());
        assertEquals(height, userWithWorstBMI.getHeight());
    }

    @Test
    void shouldReturnNull() {
        // given
        List<User> userList = List.of();

        // when
        User userWithWorstBMI = FitCalculator.findUserWithTheWorstBMI(userList);

        // then
        assertNull(userWithWorstBMI);
    }

    @Test
    void shouldReturnEqualToList() {
        // given
        List<User> userList = TestConstants.TEST_USERS_LIST;

        // when
       List<Double> bmiScores = FitCalculator.calculateBMIScore(userList);

        // then
        List<Double> expectedBmiScores = TestConstants.TEST_USERS_BMI_SCORE;
        assertEquals(expectedBmiScores, bmiScores);
    }
}
