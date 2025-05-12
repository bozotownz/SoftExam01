package dtu;

import java.util.HashSet;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;

public class LoginScreenController {


    @FXML
    private PasswordField passwordField;

    @FXML
    private Label loginStatusLabel;

    @FXML
    private Button loginButton;

    private LoginController loginController = new LoginController();

    @FXML
    public void initialize() {
        loginButton.setOnMouseClicked(this::loginButton);
        loginController.loadUsers();
        loadScheduleData();
    }
    
    private void loadScheduleData() {
        try {
            // Load the entire schedule (projects, activities, etc.) from CSV files
            Schedule schedule = CSVHandler.loadScheduleFromCSV();
            System.out.println("Data loaded successfully");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error loading data: " + e.getMessage());
        }
    }


    //Login button on main page, checks if user exists in csv file in order to proceed.
    public void loginButton(MouseEvent click) {
        try {
            if (loginController.validate(passwordField.getText())) {
                SceneManager sceneManager = SceneManager.getInstance();
                sceneManager.setCurrentUser(passwordField.getText());
                sceneManager.initPostLogin();
                sceneManager.swapToMainScreen();
            } else {
                setLoginStatusLabel("Credentials invalid, try again.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //A label for the login status
    public void setLoginStatusLabel(String text) {
        loginStatusLabel.setText(text);
    }

    //Pw field not active.
    public void resetPasswordField() {
        passwordField.clear();
    }

    
    public HashSet<String> getAllUsers() {
        return loginController.getUsers();
    }


   
}
