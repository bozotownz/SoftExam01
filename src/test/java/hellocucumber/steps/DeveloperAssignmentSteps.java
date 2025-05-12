package hellocucumber.steps;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dtu.Activity;
import dtu.LoginController;
import dtu.Project;
import dtu.Schedule;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class DeveloperAssignmentSteps {
    
    private Schedule schedule = Schedule.getInstance();
    private Project project; //also redundant
    private LoginController loginController = new LoginController(); //redundant
    private ArrayList<Activity> activities;
    private String projectLeaderName;
    private int projectID;
    private Activity activityMain;
    private String developerName;
    

        
    @Given("I {string} logged in as a project leader for project {int}")
    public void iLoggedInAsAProjectLeaderForProject(String projectLeader, int projectID) {
        this.projectLeaderName = projectLeader;
        this.projectID = projectID;
        assertEquals(projectLeaderName, schedule.findProjectByID(projectID).getProjectLeader());
    }

    @When("I assign developer {string} to activity {string}")
    public void iAssignDeveloperToActivity(String developerName, String activityName) {
        this.developerName = developerName;
        activities = schedule.findProjectByID(25001).getActivities();
        for (Activity a : activities) {
            if (a.getName().equals(activityName)) {
                activityMain = a;
                if (!activityMain.getDevelopersAssignedToActivity().contains(developerName)) {
                    activityMain.assignDeveloper(developerName);
                }
            }
        }
    }

    @Then("the developer should be assigned")
    public void theDeveloperShouldBeAssigned() {
        // check if developer has been addded
        assertTrue(activityMain.getDevelopersAssignedToActivity().contains(developerName));
    }

    @When("I remove the developer")
    public void iRemoveTheDeveloper() {
        activityMain.removeDeveloper(developerName);
    }

    @Then("the system should remove the assignment")
    public void theSystemShouldRemoveTheAssignment() {
        assertTrue(!activityMain.getDevelopersAssignedToActivity().contains(developerName));
    }
}
