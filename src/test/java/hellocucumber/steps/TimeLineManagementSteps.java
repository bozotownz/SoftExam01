package hellocucumber.steps;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import dtu.Activity;
import dtu.Schedule;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class TimeLineManagementSteps {
    

    private Schedule schedule = Schedule.getInstance();
    private Activity activity;
    
    private LocalDate startDate;
    private LocalDate endDate;
    private String activityName;
    private LocalDate changeStartDate;
    private LocalDate changeEndDate;

    //local date converter
    public LocalDate mydate(String dateString) {
        return LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
    
    @Given("an activity {string} exists with dates {string} to {string}")
    public void anActivityExistsWithDatesTo(String activityName, String startDate, String endDate) {
        this.activityName = activityName;
        this.startDate = mydate(startDate);
        this.endDate = mydate(endDate);
        activity = schedule.findProjectByID(25001).findActivityByName(activityName);
    }

    @When("I change the end date to {string}")
    public void iChangeTheEndDateTo(String string) {
        this.changeEndDate = mydate(string);
        activity.setEndDate(changeEndDate);
    }

    @Then("the activity timeline should be updated")
    public void theActivityTimelineShouldBeUpdated() {
        assertEquals(changeEndDate, activity.getEndDate());
    }
}
