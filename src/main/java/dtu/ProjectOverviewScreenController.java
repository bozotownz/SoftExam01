package dtu;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;


public class ProjectOverviewScreenController extends SubpageController {

    @FXML
    private Label projNameLabel, projIDLabel, totalTimeBudgetLabel, projLeadLabel, projLeadNameLabel;

    @FXML
    private FlowPane projActivitiesPane;

    @FXML
    private Button cancelButton, createNewActivityButton;

    private Project project;

    @FXML
    public void initialize() {
        cancelButton.setOnMouseClicked(this::cancelButton);
        createNewActivityButton.setOnMouseClicked(this::createNewActivityButton);
        setTimeBudgetLabel(0);
    }


    public void setProjOverviewScreen(Project project) {
        this.project = project;
        projNameLabel.setText(project.getProjectName());
        projIDLabel.setText(Integer.toString(project.getProjectID()));
        updateProjLeadNameLabel();
        updateTimeBudgetLabel();
        setupActivityPanes();
    }

    public void cancelButton(MouseEvent click) {
        mainScreenController.swapToAllProjectsScreen(click);
    }

    public void createNewActivityButton(MouseEvent click) {
        mainScreenController.swapToCreateActivityScreen();
    }

    public void updateTimeBudgetLabel() {
        setTimeBudgetLabel(project.getTotalProjectHours());
    }

    public void setTimeBudgetLabel(int numHours) {
        //Concat automatically casts int to string
        totalTimeBudgetLabel.setText(numHours + " Hrs");
    }

    public void updateProjLeadNameLabel() {
        //Should have hasLead() method with boolean return val in proj. This works, but kinda bad practice.
        if (project.getProjectLeader() != null) {
            setProjLeadNameLabel(project.getProjectLeader());
        } else {
            setProjLeadNameLabel("NONE");
        }
    }

    public void setProjLeadNameLabel(String projLead) {
        projLeadNameLabel.setText(projLead);
    }

    public void setupActivityPanes() {
        for (Activity activity : project.getActivities()) {
            addNewActivityTile(activity);
        }
    }

    public void addNewActivityTile(Activity activity) {
        try {
            FXMLLoader tileLoader = new FXMLLoader(getClass().getResource("ActivityTileView.fxml"));
            Parent tile = tileLoader.load();
            ActivityTileView activityTileView = tileLoader.getController();
            activityTileView.setupTile(activity, this);
            projActivitiesPane.getChildren().add(tile);

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Attempted to load empty activity or failed to load activity data. This would probably happen given the awful structure of the data necessitating awful code.");
        }

    }

}
