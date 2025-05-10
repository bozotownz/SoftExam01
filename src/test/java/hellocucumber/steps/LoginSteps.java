package hellocucumber.steps;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import dtu.LoginController;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class LoginSteps {
    private LoginController loginController = new LoginController();
    private String username;
    
    @Given("a user is not logged in") 
    public void aUserIsNotLoggedIn() {
        assertFalse(loginController.loggedIn());
    }

    @Given("a user tries to log in with credentials {string}")
    public void aUserTriesToLogInWithCredentials(String string) {
        this.username = string;
    }

    @Then("the user succesfully log in")
    public void theUserSuccesfullyLogsIn() {
        loginController.loadUsers();
        assertEquals(loginController.isUserLoggedIn(username), true);
    }

    @Then("the user does not log in")
    public void theUserDoesNotLogsIn() {
        loginController.loadUsers();
        assertEquals(loginController.isUserLoggedIn(username),false);
    }

    @Then("the text changes to {string}")
    public void theTextChangesTo(String string) {
        assertEquals(loginController.getText(),string);
    }
}
