package dtu;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;


public class EditProjectScreenController extends SubpageController {

    @FXML
    private Label projNameLabel, projIDLabel;

    @FXML
    private Button cancelButton;

    private Project project;

    @FXML
    public void initialize() {
        cancelButton.setOnMouseClicked(this::cancelButton);
    }


    public void setEditProjScreen(Project project) {
        this.project = project;
        projNameLabel.setText(project.getProjectName());
        projIDLabel.setText(Integer.toString(project.getProjectID()));
    }

    public void cancelButton(MouseEvent click) {
        mainScreenController.swapToAllProjectsScreen(click);
    }

}
