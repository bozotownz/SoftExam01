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


    protected AllProjectsScreenController allProjectsScreenController; //Preserving this reference so it doesn't get garbo'd by java.
    protected MyActivitiesScreenController myActivitiesScreenController;




    @FXML
    public void initialize() {
        logoutButton.setOnMouseClicked(this::logoutButton);
        myActivitiesButton.setOnMouseClicked(this::swapToMyActivitiesScreen);
        allProjectsButton.setOnMouseClicked(this::swapToAllProjectsScreen);

        //This is the start subscreen, only runs once obviously. This could be a lot cleaner but cba.
        currentSubScreenLabel.setText("All Projects");
        usernameLabel.setText(SceneManager.getInstance().getCurrentUser());
        initMyActivitiesScreen();
        initAllProjectsScreen();

        //loadSubScreen("AllProjectsScreen.fxml");
    }

    public void logoutButton(MouseEvent click) {
        sceneManager.swapToLoginScreen();
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

    public void initMyActivitiesScreen() {
        try {
            FXMLLoader myActivitiesScreenLoader = new FXMLLoader(getClass().getResource("MyActivitiesScreen.fxml"));
            myActivitiesScreenLoader.load();
            myActivitiesScreenController = myActivitiesScreenLoader.getController();
            myActivitiesScreenController.setMainController(this);
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

    public void swapToProjectOverviewScreen(Project project) {
        try {
            FXMLLoader projOverviewLoader = new FXMLLoader(getClass().getResource("ProjectOverviewScreen.fxml"));
            Parent root = projOverviewLoader.load();
            ProjectOverviewScreenController projectOverviewScreenController = projOverviewLoader.getController();
            projectOverviewScreenController.setProjOverviewScreen(project);
            insertSubScreen(root, projectOverviewScreenController);
            setCurrentSubScreenLabel("Project Overview");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void swapToMyActivitiesScreen(MouseEvent click) {
        loadSubScreen("MyActivitiesScreen.fxml");
        setCurrentSubScreenLabel("My Activities");
    }

    public void swapToCreateActivityScreen(Project originProject) {
        try {
            FXMLLoader createActivityScreenLoader = new FXMLLoader(getClass().getResource("CreateActivityScreen.fxml"));
            Parent root = createActivityScreenLoader.load();
            CreateActivityScreenController createActivityScreenController = createActivityScreenLoader.getController();
            createActivityScreenController.setOriginProject(originProject);
            insertSubScreen(root, createActivityScreenController);
            setCurrentSubScreenLabel("Creating New Activity");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void swapToActivityOverviewScreen(Activity activity) {
        try {
            FXMLLoader activityOverviewLoader = new FXMLLoader(getClass().getResource("ActivityOverviewScreen.fxml"));
            Parent root = activityOverviewLoader.load();
            ActivityOverviewScreenController activityOverviewScreenController = activityOverviewLoader.getController();
            activityOverviewScreenController.setActivityOverviewScreen(activity);
            insertSubScreen(root, activityOverviewScreenController);
            setCurrentSubScreenLabel("Activity Overview");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //This could've been overloaded, but I think the difference in method sigs clarifies the use case.
    public void swapToActivityOverviewScreenFromProject(Activity activity, Project project) {
        try {
            FXMLLoader activityOverviewLoader = new FXMLLoader(getClass().getResource("ActivityOverviewScreen.fxml"));
            Parent root = activityOverviewLoader.load();
            ActivityOverviewScreenController activityOverviewScreenController = activityOverviewLoader.getController();
            activityOverviewScreenController.setActivityOverviewScreen(activity, project);
            insertSubScreen(root, activityOverviewScreenController);
            setCurrentSubScreenLabel("Activity Overview");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
