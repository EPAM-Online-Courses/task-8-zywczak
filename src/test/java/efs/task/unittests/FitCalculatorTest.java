package efs.task.unittests;

import java.util.List;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.CsvFileSource;

import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.provider.EnumSource;

import java.util.Map;

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
    void shouldReturnFalse_forAllPairsFromFile_whenHeightAndWeightFromCSV(Double height, Double weight) {
        // when
        boolean bmiCorrect = FitCalculator.isBMICorrect(weight, height);

        // then
        assertFalse(bmiCorrect);
    }
    
     @Test
    void shouldReturnUserWithWorstBMI_fromUserList() {
        // given
        List<User> userList = TestConstants.TEST_USERS_LIST;

        // when
        User userWithWorstBMI = FitCalculator.findUserWithTheWorstBMI(userList);

        // then
        assertEquals(97.3, userWithWorstBMI.getWeight());
        assertEquals(1.79, userWithWorstBMI.getHeight());
    }

    @Test
    void shouldReturnNull_forEmptyUserList() {
        // given
        List<User> userList = List.of();

        // when
        User userWithWorstBMI = FitCalculator.findUserWithTheWorstBMI(userList);

        // then
        assertNull(userWithWorstBMI);
    }

    @Test
    void shouldReturnBMIScore_forUserList() {
        // given
        List<User> userList = TestConstants.TEST_USERS_LIST;

        // when
        double[] bmiScoresArray = FitCalculator.calculateBMIScore(userList);
        List<Double> bmiScores = Arrays.asList(bmiScoresArray);

        // then
        assertEquals(TestConstants.TEST_USERS_BMI_SCORE, bmiScores);
    }
}

class PlannerTest {

    private Planner planner;

    @BeforeEach
    void setUp() {
        planner = new Planner();
    }

    @ParameterizedTest(name = "Activity Level = {0}")
    @EnumSource(ActivityLevel.class)
    void shouldCalculateCorrectDailyCaloriesDemand_forAllActivityLevels(ActivityLevel activityLevel) {
        // given
        User user = TestConstants.TEST_USER;

        // when
        int calculatedCaloriesDemand = planner.calculateDailyCaloriesDemand(user, activityLevel);

        // then
        int expectedCaloriesDemand = TestConstants.CALORIES_ON_ACTIVITY_LEVEL.get(activityLevel);
        assertEquals(expectedCaloriesDemand, calculatedCaloriesDemand);
    }

    @Test
    void shouldCalculateCorrectDailyIntake_forTestUser() {
        // given
        User user = TestConstants.TEST_USER;

        // when
        DailyIntake dailyIntake = planner.calculateDailyIntake(user);

        // then
        DailyIntake expectedDailyIntake = TestConstants.TEST_USER_DAILY_INTAKE;
        assertEquals(expectedDailyIntake, dailyIntake);
    }
}
