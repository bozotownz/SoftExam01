package hellocucumber.steps;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

import dtu.Activity;
import dtu.LoginController;
import dtu.Schedule;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ActivityOverviewSteps {

    private Schedule schedule = Schedule.getInstance();
    private LoginController loginController = new LoginController();


    private ArrayList<Activity> savedActivityList = new ArrayList<>();
    private String userName;
    

    
    @When("I open activities in {string}")
    public void iOpenActivitesIn(String string) {
        savedActivityList = schedule.findProjectByName(string).getActivities();
    }

    @Then("it should list {string} and {string}")
    public void itShouldListAnd(String string, String string2) {
        assertEquals(string, savedActivityList.get(0).getName());
        assertEquals(savedActivityList.get(1).getName(), string2);
    }

    @Given("im logged in as {string}")
    public void imLoggedInAs(String string) {
        this.userName = string;
        assertEquals(loginController.isUserLoggedIn(userName), true);
    }

    @When("I open my activities")
    public void iOpenMyActivities() {
        
    }

    @Then("It should show activities assigned to me")
    public void itShouldShowActivitiesAssignedToMe() {
        
    }
}
