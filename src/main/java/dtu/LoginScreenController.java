package dtu;

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
            loginStatusLabel.setText("Data loaded successfully");
        } catch (Exception e) {
            e.printStackTrace();
            loginStatusLabel.setText("Error loading data: " + e.getMessage());
        }
    }



    public void loginButton(MouseEvent click) {
        try {
            if (loginController.validate(passwordField.getText())) {
                //Need to do it here since scenemanager does not finish constructing until after initLogin has been called, yes this could be fixed but is not really needed.
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

    //Uber-giga-redundant but it looks slightly neater which makes me happy and that's very important.
    public void setLoginStatusLabel(String text) {
        loginStatusLabel.setText(text);
    }

    public void resetPasswordField() {
        passwordField.clear();
    }




   
}
