package hellocucumber.steps;

import dtu.LoginController;
import dtu.Schedule;
import dtu.Project;
import dtu.Activity;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ProjectStaffingOverviewSteps {
    
    private Schedule schedule = Schedule.getInstance();
    private LoginController loginController = new LoginController();
    private String userName;
    private Map<String, Integer> developerActivityLoad;
    
    @Given("I am logged in as a project leader {string}")
    public void iAmLoggedInAsAProjectLeader(String projectleaderName) {
        this.userName = projectleaderName;
        loginController.loadUsers();
        assertEquals("huba", schedule.findProjectByID(25001).getProjectLeader());
        assertTrue(loginController.isUserLoggedIn(userName));
    }

    @When("I check the availability for week {string} to {string}")
    public void iCheckTheAvailabilityForWeekTo(String startDateSearch, String endDateSearch) {
        LocalDate startDate = LocalDate.parse(startDateSearch);
        LocalDate endDate = LocalDate.parse(endDateSearch);
        Project project = schedule.findProjectByID(25001);

        developerActivityLoad = project.getDeveloperActivityLoadForInterval(startDate, endDate);

    }

    @Then("the system should display a list of developers and their current activity load for that week")
    public void theSystemShouldDisplayAListOfDevelopersAndTheirCurrentActivityLoadForThatWeek() {
        assertTrue(developerActivityLoad.containsKey("loni"));
        assertEquals(1, developerActivityLoad.get("loni"));
    }
}
