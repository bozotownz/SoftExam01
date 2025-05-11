package dtu;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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


    protected AllProjectsScreenController allProjectsScreenController; //Preserving this reference so it doesn't get garbo'd by java.



    @FXML
    public void initialize() {
        logoutButton.setOnMouseClicked(this::logoutButton);
        myActivitiesButton.setOnMouseClicked(this::swapToMyActivitiesScreen);
        allProjectsButton.setOnMouseClicked(this::swapToAllProjectsScreen);

        //This is the start subscreen, only runs once obviously. This could be a lot cleaner but cba.
        currentSubScreenLabel.setText("All Projects");
        initAllProjectsScreen();
        //loadSubScreen("AllProjectsScreen.fxml");
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


    //This is major voodoo, don't judge me.
    private void loadSubScreen(String fxml) {
        try {
            FXMLLoader subpageLoader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = subpageLoader.load();

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


    private void insertSubScreen(Parent root, SubpageController subpageController) {
        if (subpageController != null) {
            subpageController.setMainController(this);
        }
        subScreenAnchorPane.getChildren().setAll(root);
        AnchorPane.setTopAnchor(root, 0.0);
        AnchorPane.setRightAnchor(root, 0.0);
        AnchorPane.setBottomAnchor(root, 0.0);
        AnchorPane.setLeftAnchor(root, 0.0);
    }

    public void initAllProjectsScreen() {
        try {
            FXMLLoader allProjScreenLoader = new FXMLLoader(getClass().getResource("AllProjectsScreen.fxml"));
            Parent root = allProjScreenLoader.load();
            allProjectsScreenController = allProjScreenLoader.getController();
            insertSubScreen(root, allProjectsScreenController);
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

    public void swapToEditProjectScreen(Project project) {
        try {
            FXMLLoader editProjLoader = new FXMLLoader(getClass().getResource("EditProjectScreen.fxml"));
            Parent root = editProjLoader.load();
            EditProjectScreenController editProjectScreenController = editProjLoader.getController();
            editProjectScreenController.setEditProjScreen(project);
            insertSubScreen(root, editProjectScreenController);
        } catch (Exception e) {
            e.printStackTrace();
        }
        setCurrentSubScreenLabel("Project Overview");
    }




}
