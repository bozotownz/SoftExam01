package hellocucumber.steps;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import dtu.Activity;
import dtu.Schedule;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class BudgetManagementSteps {

    private Schedule schedule = Schedule.getInstance();
    private Activity activity;
    private ArrayList<Activity> activityArrayList = new ArrayList<>();

    private int currentBudgetHours;
    private String activityName;
    private int expectedHourChange;
    

    @Given("an activity {string} has a budget of {int} hours")
    public void anActivityHasABudgetOfHours(String activity, Integer hours) {
        this.activityName = activity;
        this.currentBudgetHours = hours;

        activityArrayList = schedule.findProjectByID(25001).getActivities();
        for (Activity a : activityArrayList) {
            if (a.getName().equals(activityName)) {
                assertEquals(hours, a.getBudgetHours());
                this.activity = a;
            }
        }
        
    }

    @When("I update the budget to {int} hours")
    public void iUpdateTheBudgetToHours(Integer newBudgetHours) {
        expectedHourChange = newBudgetHours;
        this.activity.setBudgetHours(newBudgetHours); 
    }

    @Then("the system should reflect the new budgeted time")
    public void theSystemShouldReflectTheNewBudgetedTime() {
       assertEquals(expectedHourChange, activity.getBudgetHours());
    }

    @Then("the system should reject the change")
    public void theSystemShouldRejectTheChange() {
        assertFalse(activity.getBudgetHours() >= 0); 
    }
}
