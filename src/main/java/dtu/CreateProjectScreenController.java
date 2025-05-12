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

    private String currentLeader = null, none = "NONE";



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
        resetComboBoxItems(null);
        selectProjectManagerField.setItems(assignableUsersList);
    }

    public void setupLeaderSelection() {
        selectProjectManagerField.setOnAction(event -> {
            String selected = selectProjectManagerField.getValue();

            if (selected == null) return;

            if (selected.equals(none)) {
                currentLeader = null;
                resetComboBoxItems(null);
            } else if (!selected.equals(currentLeader)) {
                currentLeader = selected;
                resetComboBoxItems(currentLeader);
                selectProjectManagerField.setValue(currentLeader);
            }
        });
    }


    private void resetComboBoxItems(String selectedLeader) {
        ObservableList<String> newItems = FXCollections.observableArrayList();
        newItems.add(none); // Always at the top

        // Add all users except the currently selected leader (if any)
        for (String user : allUsersList) {
            if (!user.equals(selectedLeader)) {
                newItems.add(user);
            }
        }

        // If a leader is selected, add them back for display
        if (selectedLeader != null) {
            newItems.add(1, selectedLeader); // Keep leader near top
        }

        selectProjectManagerField.setItems(newItems);
    }



}
