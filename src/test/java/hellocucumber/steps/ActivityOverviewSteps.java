package hellocucumber.steps;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

import dtu.Activity;
import dtu.LoginController;
import dtu.Project;
import dtu.Schedule;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ActivityOverviewSteps {

    private Schedule schedule = Schedule.getInstance();
    private LoginController loginController = new LoginController();
    private ArrayList<Activity> myAssignedActivities = new ArrayList<>();
    private ArrayList<Activity> savedActivityList = new ArrayList<>();
    private final Activity activityTestOne = new Activity("Requirements Analysis", 100, LocalDate.of(2025,10,01), LocalDate.of(2025,10,14));
    private ArrayList<Activity> expectedActivities = new ArrayList<>();
    private String userName;
    

    
    @When("I open activities in {string}")
    public void iOpenActivitesIn(String string) {
        this.expectedActivities.add(activityTestOne);
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
        loginController.loadUsers();
        assertEquals(loginController.isUserLoggedIn(userName), true);
    }

    @When("I open my activities")
    public void iOpenMyActivities() {
        for(Project projects : schedule.getProjects()) {
            for (Activity a : projects.getActivities()) {
                if (a.getDevelopersAssignedToActivity().contains(userName)) {
                    myAssignedActivities.add(a);
                }
            }
        }  
    }

    @Then("It should show {string}")
    public void itShouldShowActivitiesAssignedToMe(String expectedActivity) {
        //actName1 not used. Just to represent activity name
        expectedActivities.add(activityTestOne);
        assertEquals(expectedActivities.get(0).getName(), myAssignedActivities.get(0).getName());
        assertEquals(expectedActivities.get(0).getBudgetHours(), myAssignedActivities.get(0).getBudgetHours());
        assertEquals(expectedActivities.get(0).getStartDate(), myAssignedActivities.get(0).getStartDate());
        assertEquals(expectedActivities.get(0).getEndDate(), myAssignedActivities.get(0).getEndDate());
    }
}
