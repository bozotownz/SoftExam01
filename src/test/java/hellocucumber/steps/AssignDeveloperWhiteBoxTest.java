package hellocucumber.steps;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dtu.Activity;
import org.junit.jupiter.api.Test;

import dtu.Activity;

public class AssignDeveloperWhiteBoxTest {
    private Activity activity;

    @BeforeEach
    public void setUp() {
        activity = new Activity("Test Activity", 100, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 12, 31));
    }

    @Test
    public void testPath1_DeveloperDoesNotExist() {
        assertTrue(activity.getDevelopersAssignedToActivity().isEmpty());

        activity.assignDeveloper("John");

        assertEquals(1, activity.getDevelopersAssignedToActivity().size());
        assertTrue(activity.getDevelopersAssignedToActivity().contains("John"));
    }

    @Test
    public void testPath2_DeveloperAlreadyExists() {
        // Test for Path 2: 1,2(false),4
        // Input Set B: assignedDevelopersActivity = ["John"], developerName = "John"
        
        // Setup initial state
        activity.assignDeveloper("John");
        int initialSize = activity.getDevelopersAssignedToActivity().size();
        
        // Execute the method again with same developer
        activity.assignDeveloper("John");
        
        // Verify no changes occurred
        assertEquals(initialSize, activity.getDevelopersAssignedToActivity().size());
    }
}