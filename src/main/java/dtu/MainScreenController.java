package dtu;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class MainScreenController {

    private SceneManager sceneManager = SceneManager.getInstance();

    @FXML
    private Button logoutButton, myActivitiesButton, allProjectsButton;

    @FXML
    private Label usernameLabel, currentSubScreenLabel;

    @FXML
    private AnchorPane subScreenAnchorPane;


    @FXML
    public void initialize() {
        logoutButton.setOnMouseClicked(this::logoutButton);
        myActivitiesButton.setOnMouseClicked(this::swapToMyActivitiesScreen);
        allProjectsButton.setOnMouseClicked(this::swapToAllProjectsScreen);

        //This is the start subscreen, only runs once obviously. This could be a lot cleaner but cba.
        currentSubScreenLabel.setText("All Projects");
        loadSubScreen("AllProjectsScreen.fxml");
    }

    public void logoutButton(MouseEvent click) {
        sceneManager.swapToLoginScreen();
    }

    public void setUsernameLabel(String username) {
        usernameLabel.setText(username);
    }



    public void setCurrentSubScreenLabel(String currentSubScreen) {
        currentSubScreenLabel.setText(currentSubScreen);
    }


    //Subscreen container dims are 1140x670 by default (Personal note - delete later)
    private void loadSubScreen(String fxml) {
        try {
            FXMLLoader subpageLoader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = subpageLoader.load();

            //This is major voodoo, don't judge me.
            Object subpageController = subpageLoader.getController();
            if (subpageController instanceof SubpageController) {
                ((SubpageController) subpageController).setMainController(this);
            }


            subScreenAnchorPane.getChildren().setAll(root);
            AnchorPane.setTopAnchor(root, 0.0);
            AnchorPane.setRightAnchor(root, 0.0);
            AnchorPane.setBottomAnchor(root, 0.0);
            AnchorPane.setLeftAnchor(root, 0.0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    public void swapToAllProjectsScreen(MouseEvent click) {
        loadSubScreen("AllProjectsScreen.fxml");
        setCurrentSubScreenLabel("All Projects");
    }

    public void swapToCreateNewProjectScreen() {
        loadSubScreen("CreateProjectScreen.fxml");
        setCurrentSubScreenLabel("Creating New Project");
    }

    public void swapToMyActivitiesScreen(MouseEvent click) {
        loadSubScreen("MyActivitiesScreen.fxml");
        setCurrentSubScreenLabel("My Activities");
    }




}
