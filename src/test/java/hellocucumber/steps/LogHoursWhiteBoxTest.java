package hellocucumber.steps;

import dtu.Activity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class LogHoursWhiteBoxTest {
    private Activity activity;

    @BeforeEach
    public void setUp() {
        activity = new Activity("Test Activity", 100, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 12, 31));
    }

    @Test
    public void testPath1_DeveloperWithExistingHours() {
        // Test for Path 1,2(true),3,5,6
        // Input Set A: developersLoggedHours = {"Alice": 2}, developerName = "Alice", hoursToLog = 5

        // Setup initial state
        activity.assignDeveloper("Alice");
        activity.logHours("Alice", 2); // Initial hours
        
        // Execute the method
        activity.logHours("Alice", 5);
        
        // Verify expected results
        assertEquals(7, activity.getTotalHoursLoggedForDeveloper("Alice")); // 2 + 5 = 7
        assertEquals(7, activity.getTotalHoursLogged()); // Total hours should also be 7
    }

    @Test
    public void testPath2_DeveloperWithNoHours() {
        // Test for Path 1,2(false),4,5,6
        // Input Set B: developersLoggedHours = {"Bob": 0}, developerName = "Bob", hoursToLog = 3

        // Setup initial state - developer exists but hasn't logged hours yet
        activity.assignDeveloper("Bob");
        
        // Execute the method
        activity.logHours("Bob", 3);
        
        // Verify expected results
        assertEquals(3, activity.getTotalHoursLoggedForDeveloper("Bob"));
        assertEquals(3, activity.getTotalHoursLogged());
    }
}