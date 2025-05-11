package dtu;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.List;

public class CreateProjectScreenController extends SubpageController {

    @FXML
    private TextField projectNameField;

    @FXML
    private ComboBox selectProjectManagerField;

    @FXML
    private Button createProjectButton, cancelButton;

    @FXML
    private Label errorMessageLabel;



    @FXML
    public void initialize() {
        createProjectButton.setOnMouseClicked(this::createProjectButton);
        cancelButton.setOnMouseClicked(this::cancelButton);

        constructUsersList();
    }

    public void createProjectButton(MouseEvent click) {
        //This should probably be a switch

        if (projectNameField.getText().isEmpty()) {
            errorMessageLabel.setText("Project Name Cannot Be Empty");
            throw new RuntimeException("Project Name Cannot Be Empty");
        } else if (projectNameField.getText().length() > 30) { //Arbitrary limit - This isn't very robust and should ideally be text formatting instead if scalability is needed.
            errorMessageLabel.setText("Project Name Cannot Exceed 30 Symbols");
            throw new RuntimeException("Project Name Cannot Exceed 30 Symbols");
        } else {
            try {
                Schedule currentSchedule = Schedule.getInstance();
                Project newProj = currentSchedule.createProject(projectNameField.getText()); //Need the ref to construct the tile without reload of db.
                currentSchedule.addProject(newProj);
                //ADD THE PROJECT TILE TO THE PROJECT SCREEN
                mainScreenController.allProjectsScreenController.addNewProjectTile(newProj);
                mainScreenController.swapToAllProjectsScreen(click);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void cancelButton(MouseEvent click) {
        mainScreenController.swapToAllProjectsScreen(click);
    }

    public void something() {

    }

    public void constructUsersList() {
        //Construct a list of users


        List<String> userList = new ArrayList<>();
        userList.add("test1");
        userList.add("test2");
        selectProjectManagerField.setItems(FXCollections.observableArrayList(userList));
    }



}
