package hellocucumber.steps;
import dtu.Activity;
import dtu.Project;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FindActivityByNameWhiteBoxTest {

    private Project project;

    @BeforeEach
    public void setUp() {
        project = new Project("Project Alpha", 25001);
        project.setProjectLeader("huba");
    }

    @Test
    public void testFindActivityByNameExists() {
        Activity activity = new Activity("Tables");
        project.addActivity(activity);
        Activity result = project.findActivityByName("Tables");

        assertNotNull(result);
        assertEquals("Tables", result.getName());
    }

    @Test
    public void testFindActivityByNameDoesNotExist() {
        Activity activity = new Activity("Test");
        project.addActivity(activity);
        Activity result = project.findActivityByName("Tables");

        assertNull(result);
    }
}
