package dtu;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class CreateActivityScreenController extends SubpageController {

    @FXML
    private Button cancelButton, createActivityButton;

    private Project originProject;

    @FXML
    public void initialize() {
        cancelButton.setOnMouseClicked(this::cancelButton);
    }

    public void cancelButton(MouseEvent click) {
        mainScreenController.swapToProjectOverviewScreen(originProject);
    }

    public void createActivityButton() {
        //GET INFO FROM FIELDS HERE

        //Activity activity = new Activity()
        //originProject.addActivity(activity);
    }

    public void setOriginProject(Project originProject) {
        this.originProject = originProject;
    }
}
