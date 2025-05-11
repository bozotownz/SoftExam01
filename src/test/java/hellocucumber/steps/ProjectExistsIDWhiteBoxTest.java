package hellocucumber.steps;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dtu.Project;
import dtu.Schedule;

public class ProjectExistsIDWhiteBoxTest {

    private Schedule schedule = Schedule.getInstance();

    @BeforeEach
    public void setup() {
        schedule.reset();
    }

    @Test
    public void test1ProjectExists() {
        Project project = new Project("Test Project", 25001);
        schedule.addProject(project);

        assertTrue(schedule.projectExistsID(25001));

    }

    @Test
    public void test2ProjectDoesNotExist() {
        //The projects list is empty. therefore .projectExistsId will return false
        assertFalse(schedule.projectExistsID(25001));
    }
    
}
