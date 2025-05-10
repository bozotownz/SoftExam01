package hellocucumber.steps;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dtu.LoginController;
import dtu.Project;
import dtu.Schedule;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ProjectManagementSteps {
    private LoginController loginController = new LoginController();
    private Project project;
    private String projectName;
    private Schedule schedule = Schedule.getInstance();
    private String projectLeaderName;

    @Given("there exists no projects")
    public void thereExistsNoProjects() {
        schedule.reset();
    }

    @When("the user creates a project named {string}")
    public void theUserCreatesAProjectNamed(String string) {
        this.projectName = string;
        //this.project = this.schedule.createProject(projectName);
    }

    @Then("the system should generate a project number in the format {int}")
    public void TheSystemShouldGenerateAProjectNumberInTheFormat(int integer) {
        this.project = this.schedule.createProject(projectName);
        this.schedule.addProject(project);
        assertEquals(projectName, project.getProjectName());
        assertEquals(integer, project.getProjectID());
    }

    @Then("I should receive a confirmation message {string}")
    public void iShouldReceiveAConfirmationMessage(String string) {
        assertEquals(this.schedule.getRespondText(), string);
    }

    @Given("a project named {string} exists")
    public void aProjectNamedExists(String string) {
        assertTrue(this.schedule.projectExistsName(string));
    }

    @When("I delete project {string}")
    public void iDeleteProject(String string) {
        assertTrue(this.schedule.removeProject(string));
    }

    @Then("the system should remove the project and all associated data")
    public void theSystemShouldRemoveTheProjectAndAllAssociatedData() {
        //gjort ovenfor
    }

    
    @When(  "I assign a project leader named {string}")
    public void iAssignAProjectLeaderNamed(String string) {
        this.projectLeaderName = string;
    }

    @Then("The system should assign {string} to the project {string}")
    public void theSystemShouldAssignToTheProject(String projleadername, String projname) {
        schedule.findProjectByName(projname).setProjectLeader(projectLeaderName);
        assertEquals(projleadername, schedule.findProjectByName(projname).getProjectLeader());
    }
}
