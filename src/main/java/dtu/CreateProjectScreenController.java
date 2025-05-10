package dtu;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
    public void initialize() {
        createProjectButton.setOnMouseClicked(this::createProjectButton);
        cancelButton.setOnMouseClicked(this::cancelButton);

        constructUsersList();
    }

    public void createProjectButton(MouseEvent click) {
        if (!projectNameField.getText().isEmpty()) {
            try {
                //ADD LOGIC HERE TO ACTUALLY ADD IT
                Schedule.getInstance().createProject(projectNameField.getText());



            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            throw new RuntimeException("Project name cannot be empty");
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
