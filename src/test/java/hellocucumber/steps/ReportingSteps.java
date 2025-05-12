package hellocucumber.steps;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

import dtu.Activity;
import dtu.Project;
import dtu.Schedule;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ReportingSteps {

    private Schedule schedule = Schedule.getInstance();
    private Project project;
    private Activity activity;
    private Map<String, Integer> reportOfHours = new HashMap<>();
    
    @Given("project {int} has recorded hours")
    public void projectHasRecordedHours(int projectID) {
        project = schedule.findProjectByID(projectID);
        assertTrue(project.getTotalProjectHours() > 0);
    }

    @When("I generate a report")
    public void iGenerateAReport() {
        //making a map of <Activity , Hourslogged> and then also total hours logged
        int totalHours = project.getTotalProjectHours();
        for (Activity a : project.getActivities()) {
            reportOfHours.put(a.getName(), a.getTotalHoursLogged());
        }
        reportOfHours.put("Project: " + project.getProjectName(), totalHours);
    }

    @Then("it lists all hours spent")
    public void itListsAllHoursSpent() {
        //output reportOfHours 
        //not checking all index, just those given in feature
        //activity
        assertTrue(reportOfHours.containsKey("Tempo"));
        assertTrue(reportOfHours.get("Tempo") == 13);

        //for project
        assertTrue(reportOfHours.containsKey("Project: Project Alpha"));
        assertTrue(reportOfHours.get("Project: Project Alpha") == 13);


    }
}
