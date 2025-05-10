package hellocucumber.steps;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dtu.Activity;
import dtu.LoginController;
import dtu.Schedule;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class TimeLoggingSteps {
        
    private Schedule schedule = Schedule.getInstance();
    private LoginController loginController = new LoginController();
    private Activity activity;
    private String developerName;
    private int hoursToLog;
    private int previousHours;
    private String activityName;
    
    
    @Given("I am logged in as developer {string}")
    public void iAmLoggedInAsDeveloper(String developer) {
        developerName = developer;
        loginController.loadUsers();
        //Anyone can log hours for an activity. doesn't technically have to be assigned develpoer. 
        assertEquals("loni", schedule.findProjectByID(25001).getProjectLeader());
        assertTrue(loginController.isUserLoggedIn(developerName));
    }

    @When("I enter {int} hours for {string}")
    public void iEnterHoursFor(Integer int1, String activityName) {
        hoursToLog = int1;
        this.activityName = activityName;
        activity = schedule.findProjectByID(25001).findActivityByName(activityName);

        previousHours = activity.getTotalHoursLoggedForDeveloper(developerName);
        activity.logHours(developerName, hoursToLog);
    }

    @Then("the system should record my entry")
    public void theSystemShouldRecordMyEntry() {
        //kinda... pmtd
        assertEquals(previousHours+hoursToLog, activity.getTotalHoursLoggedForDeveloper(developerName));
    }
}
