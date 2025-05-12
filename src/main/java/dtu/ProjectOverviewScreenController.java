package dtu;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class ProjectOverviewScreenController extends SubpageController {

    @FXML
    private Label projNameLabel, projIDLabel, totalTimeBudgetLabel, projLeadLabel, projLeadNameLabel;

    @FXML
    private TextField projectNameField;

    @FXML
    private ComboBox<String> selectProjectManagerField;

    @FXML
    private AnchorPane projOverviewPane, projEditPane;

    @FXML
    private FlowPane projActivitiesPane;

    @FXML
    private Button cancelButton, createNewActivityButton, editProjButton, cancelEditProjButton, saveChangesButton, deleteProjButton;

    private Project project;

    private final List<String> allUsersList = new ArrayList<>();

    private ObservableList<String> assignableUsersList;

    private String currentLeader = null;

    @FXML
    public void initialize() {
        cancelButton.setOnMouseClicked(this::cancelButton);
        createNewActivityButton.setOnMouseClicked(this::createNewActivityButton);
        editProjButton.setOnMouseClicked(this::editProjButton);
        cancelEditProjButton.setOnMouseClicked(this::cancelEditButton);
        deleteProjButton.setOnMouseClicked(this::deleteProjButton);
        saveChangesButton.setOnMouseClicked(this::saveChangesButton);
        setTimeBudgetLabel(0);
        setupUsersList();
        setupLeaderSelection();
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

    public void editProjButton(MouseEvent click) {
        togglePage();
        setupEditPage();
    }

    public void cancelEditButton(MouseEvent click) {
        togglePage();
    }

    public void createNewActivityButton(MouseEvent click) {
        mainScreenController.swapToCreateActivityScreen(project);
    }

    public void updateTimeBudgetLabel() {
        setTimeBudgetLabel(project.getBudgetProjectHours());
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
            activityTileView.setOriginProject(project);
            projActivitiesPane.getChildren().add(tile);

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Attempted to load empty activity or failed to load activity data. This would probably happen given the awful structure of the data necessitating awful code.");
        }

    }

    public Project getProject() {
        return project;
    }

    private void togglePage() {
        projOverviewPane.setDisable(!projOverviewPane.isDisabled());
        projOverviewPane.setVisible(!projOverviewPane.isVisible());

        projEditPane.setDisable(!projEditPane.isDisable());
        projEditPane.setVisible(!projEditPane.isVisible());
    }

    private void setupEditPage() {
        projectNameField.setText(project.getProjectName());
        if (project.getProjectLeader().isEmpty()) {
            selectProjectManagerField.setValue("NONE");
        } else {
            selectProjectManagerField.setValue(project.getProjectLeader());
        }
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

    private void deleteProjButton(MouseEvent click) {
        Schedule.getInstance().deleteProjectByRef(project);
        mainScreenController.swapToAllProjectsScreen(click);
    }

    private void saveChangesButton(MouseEvent click) {
        project.setProjectLeader(selectProjectManagerField.getValue());
        projLeadNameLabel.setText(project.getProjectLeader());
        togglePage();
    }



}
