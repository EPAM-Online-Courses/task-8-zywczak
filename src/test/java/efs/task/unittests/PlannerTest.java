package efs.task.unittests;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

     @ParameterizedTest(name = "activityLevel = {0}")
    @EnumSource(ActivityLevel.class)
    void shouldCalculateDailyCaloriesDemand(ActivityLevel activityLevel) {
        // given
        User user = TestConstants.TEST_USER;

        // when
        int calculatedCalories = planner.calculateDailyCaloriesDemand(user, activityLevel);

        // then
        Map<ActivityLevel, Integer> expectedCaloriesMap = TestConstants.CALORIES_ON_ACTIVITY_LEVEL;
        int expectedCalories = expectedCaloriesMap.get(activityLevel);
        assertEquals(expectedCalories, calculatedCalories);
    }
    
    @Test
    void shouldCalculateDailyIntake() {
        // given
        User user = TestConstants.TEST_USER;
        
        // when
        DailyIntake dailyIntake = planner.calculateDailyIntake(user);

        // then
        DailyIntake expectedDailyIntake = TestConstants.TEST_USER_DAILY_INTAKE;
        assertEquals(dailyIntake.getCalories, expectedDailyIntake.getCalories);
        assertEquals(dailyIntake.getProtein(), expectedDailyIntake.getProtein());
        assertEquals(dailyIntake.getFat(), expectedDailyIntake.getFat());
        assertEquals(dailyIntake.getCarbohydrate(), expectedDailyIntake.getCarbohydrate());
    }
}
