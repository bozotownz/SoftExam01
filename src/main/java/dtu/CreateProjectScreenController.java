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
import java.util.stream.Collectors;

public class CreateProjectScreenController extends SubpageController {

    @FXML
    private TextField projectNameField;

    @FXML
    private ComboBox<String> selectProjectManagerField;

    @FXML
    private Button createProjectButton, cancelButton;

    @FXML
    private Label errorMessageLabel;

    private final List<String> allUsersList = new ArrayList<>();

    private ObservableList<String> assignableUsersList;

    private String currentLeader = null;



    @FXML
    public void initialize() {
        createProjectButton.setOnMouseClicked(this::createProjectButton);
        cancelButton.setOnMouseClicked(this::cancelButton);

        setupUsersList();
        setupLeaderSelection();
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
                if (currentLeader != null) {
                    newProj.setProjectLeader(selectProjectManagerField.getValue());
                }
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


    public void setupUsersList() {
        allUsersList.addAll(SceneManager.getInstance().getAllUsers());
        assignableUsersList = FXCollections.observableArrayList(allUsersList);
        selectProjectManagerField.setValue(null);
        selectProjectManagerField.setItems(assignableUsersList);
    }

    public void setupLeaderSelection() {
        selectProjectManagerField.setOnAction(event -> {
            String newLeader = selectProjectManagerField.getValue();

            // Skip if same as before or null
            if (newLeader == null || newLeader.equals(currentLeader)) return;

            currentLeader = newLeader;

            // Rebuild list: show all users except the selected one
            List<String> updatedList = allUsersList.stream()
                    .filter(user -> !user.equals(currentLeader))
                    .collect(Collectors.toList());

            selectProjectManagerField.setItems(FXCollections.observableArrayList(updatedList));
            selectProjectManagerField.getItems().add(0, currentLeader); // keep selected at top
            selectProjectManagerField.setValue(currentLeader);
        });
    }



}
