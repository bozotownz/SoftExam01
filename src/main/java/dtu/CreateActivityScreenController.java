package dtu;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CreateActivityScreenController extends SubpageController {

    @FXML
    private Button cancelButton, createActivityButton;

    @FXML
    private TextField timeBudgetField, activityNameField;

    @FXML
    private DatePicker startDateField, endDateField;

    @FXML
    private Label timeBudgetErrorLabel, activityNameErrorLabel;

    @FXML
    private ListView<String> assignedDevsListView, unassignedDevsListView;

    private Set<String> allDevs;

    private final ObservableList<String> assignedDevs = FXCollections.observableArrayList(), unassignedDevs = FXCollections.observableArrayList();

    private Project originProject;

    @FXML
    public void initialize() {
        cancelButton.setOnMouseClicked(this::cancelButton);
        createActivityButton.setOnMouseClicked(this::createActivityButton);

        allDevs = SceneManager.getInstance().getAllUsers();

        //The list 'add devs' contains the unassigned devs and vice versa, hence the odd naming scheme.
        unassignedDevsListView.setItems(unassignedDevs);
        assignedDevsListView.setItems(assignedDevs);

        updateDevsLists();
        setupAddRemoveOnClick();
        restrictTimeBudgetField();
        restrictDatePicker();
    }


    private void updateDevsLists() {
        //Make a copied set of all the devs and remove the ones that are assigned. This gives us a list of the ones that are unassigned.
        Set<String> unassignedSet = new HashSet<>(allDevs);
        assignedDevs.forEach(unassignedSet::remove); //This is faster than removeAll because... Java?

        unassignedDevs.setAll(unassignedSet);
    }

    public void cancelButton(MouseEvent click) {
        mainScreenController.swapToProjectOverviewScreen(originProject);
    }

    private void createActivityButton(MouseEvent click) {
        //GET INFO FROM FIELDS HERE

        Activity activity = new Activity(getActivityName(), getTimeBudget(), getStartDate(), getEndDate());
        for (String dev : assignedDevs) {
            activity.assignDeveloper(dev);
        }
        originProject.addActivity(activity);
        mainScreenController.swapToProjectOverviewScreen(originProject);
    }

    public void setOriginProject(Project originProject) {
        this.originProject = originProject;
    }

    private void setupAddRemoveOnClick() {
        unassignedDevsListView.setOnMouseClicked(event -> {
            String selected = unassignedDevsListView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                assignedDevs.add(selected);
                updateDevsLists();
            }
        });

        assignedDevsListView.setOnMouseClicked(event -> {
            String selected = assignedDevsListView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                assignedDevs.remove(selected);
                updateDevsLists();
            }
        });
    }

    private void restrictTimeBudgetField() {
        timeBudgetField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[0-9]*\\.?[0-9]*")) {
                timeBudgetField.setText(oldValue);
            }
        });
    }

    private String getActivityName() {
        if (activityNameField.getText().isEmpty()) {
            activityNameErrorLabel.setText("Activity Name cannot be empty");
            throw new RuntimeException("Activity Name empty");
        } else if (activityNameField.getText().length() > 20) {
            activityNameErrorLabel.setText("Activity Name cannot be longer than 20 characters");
            throw new RuntimeException("Activity Name too long");
        } else {
            return activityNameField.getText();
        }
    }

    private int getTimeBudget() {
        int timeBudget = Integer.parseInt(timeBudgetField.getText());

        if (timeBudget <= 0) {
            timeBudgetErrorLabel.setText("Time Budget has to be 1 hour or more");
            throw new RuntimeException("Time budget was 0 or less");
        } else {
            return timeBudget;
        }
    }

    private LocalDate getStartDate() {
        return startDateField.getValue();
    }

    private LocalDate getEndDate() {
        return endDateField.getValue();
    }

    private void restrictDatePicker() {
        //Adds two listeners that update the dayCell items and disable them whenever the value of the opposing datePicker field is updated.
        //TLDR; When startDate is updated, endDate is restricted and vice-versa.


        startDateField.valueProperty().addListener((observable, oldValue, newValue) -> {
            //If value isnt empty
            if (newValue != null) {
                //Value change
                endDateField.setValue(endDateField.getValue() != null && endDateField.getValue().isBefore(newValue)
                        ? newValue
                        : endDateField.getValue());

                //Overrides the factory method for the dayCells
                endDateField.setDayCellFactory(picker -> new javafx.scene.control.DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        //If our dayCell is before the updated newValue, disable it.
                        if (item != null && item.isBefore(newValue)) {
                            setDisable(true);
                            setStyle("-fx-background-color: #ffcccc;");
                        } else {
                            setDisable(false);
                            setStyle("");
                        }
                    }
                });
            }
        });

        //Same as above but inverted.
        endDateField.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                startDateField.setValue(startDateField.getValue() != null && startDateField.getValue().isAfter(newValue)
                        ? newValue
                        : startDateField.getValue());
                startDateField.setDayCellFactory(picker -> new javafx.scene.control.DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        //Here it is obviously after, rather than before.
                        if (item != null && item.isAfter(newValue)) {
                            setDisable(true);
                            setStyle("-fx-background-color: #ffcccc;");
                        } else {
                            setDisable(false);
                            setStyle("");
                        }
                    }
                });
            }
        });
    }

}
