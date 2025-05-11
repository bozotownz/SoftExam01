package hellocucumber.steps;

import dtu.Activity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class RemoveDeveloperWhiteBoxTest {
    private Activity activity;

    @BeforeEach
    public void setUp() {
        activity = new Activity("Test Activity", 100, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 12, 31));
    }

    @Test
    public void testPath1_DeveloperExists() {
        // Setup initial state
        activity.assignDeveloper("John");
        activity.assignDeveloper("Lisa");
        
        // Execute the method
        activity.removeDeveloper("John");
        
        // Verify expected results
        assertFalse(activity.getDevelopersAssignedToActivity().contains("John"));
        assertTrue(activity.getDevelopersAssignedToActivity().contains("Lisa"));
        assertEquals(1, activity.getDevelopersAssignedToActivity().size());
    }

    @Test
    public void testPath2_DeveloperDoesNotExist() {
        // Setup initial state
        activity.assignDeveloper("Lisa");
        int initialSize = activity.getDevelopersAssignedToActivity().size();
        
        // Execute the method
        activity.removeDeveloper("John");
        
        // Verify no changes occurred
        assertEquals(initialSize, activity.getDevelopersAssignedToActivity().size());
        assertTrue(activity.getDevelopersAssignedToActivity().contains("Lisa"));
    }
}