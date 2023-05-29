package efs.task.unittests;

import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class PlannerTest {

    private Planner planner;

    @BeforeEach
    void setUp() {
        planner = new Planner();
    }

    @ParameterizedTest(name = "Activity Level={0}")
    @EnumSource(ActivityLevel.class)
    void shouldCalculateDailyCaloriesDemand_forAllActivityLevels(ActivityLevel activityLevel) {
        // given
        User user = TestConstants.TEST_USER;

        // when
        int calculatedCalories = planner.calculateDailyCaloriesDemand(user, activityLevel);

        // then
        int expectedCalories = TestConstants.CALORIES_ON_ACTIVITY_LEVEL.get(activityLevel);
        assertEquals(expectedCalories, calculatedCalories);
    }

    @Test
    void shouldCalculateDailyIntake_forTestUser() {
        // given
        User user = TestConstants.TEST_USER;

        // when
        NutrientIntake dailyIntake = planner.calculateDailyIntake(user);

        // then
        NutrientIntake expectedIntake = TestConstants.TEST_USER_DAILY_INTAKE;
        assertEquals(expectedIntake, dailyIntake);
    }
}
