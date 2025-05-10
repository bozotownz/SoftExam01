package hellocucumber.steps;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertTrue;

import dtu.Activity;
import dtu.Project;
import dtu.Schedule;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ActivityManagementSteps {
    private Schedule schedule = Schedule.getInstance();
    private Project project;
    private Activity activity;
    private String projectName;
    private String activityName;
    private int projectID;
    private int budgetHours;


    public LocalDate mydate(String dateString) {
        return LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    @Given("a project with projectID {int} exists")
    public void aProjectWithProjectIDExists(int projectID) {
        assertTrue(schedule.projectExistsID(projectID));
    }

    @When("I create a new activity {string} in the project with projectID {int}")
    public void iCreateANewActivityInTheProjectWithProjectID(String actName, int string2) {
        this.activityName = actName;
        this.projectID = string2;
        //activity = new Activity(actName);
        //this.project.addActivity(activity);
    }

    @When("set a budget of {int} hours")
    public void setABudgetOfHours(int budgetHours) {
        this.budgetHours = budgetHours;
        //this.activity.setBudgetedHours(int1);
    }

    @When("set the start date to {string} and end date to {string}")
    public void setTheStartDateToAndEndDateTo(String startdate, String endDate) {
        activity = new Activity(this.activityName, this.budgetHours, mydate(startdate), mydate(endDate));
        //activity.setStartDate(LocalDate.parse(string));
        //activity.setEndDate(LocalDate.parse(string2));
    }
    
    @Then("the activity should be added to the project")
    public void theActivityShouldBeAddedToTheProject() {
        schedule.findProjectByID(projectID).addActivity(activity);
        assertTrue(schedule.findProjectByID(projectID).getActivities().contains(activity));
    }
}
