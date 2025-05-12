package dtu;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

public class ActivityOverviewScreenController extends SubpageController {

    private Activity activity; //GET THIS FROM THE CLICKED ACTIVITY TILE
    private Project originProject; //REFERENCE TO SEND YOU BACK TO PROJ OVERVIEW IF NEEDED
    private boolean hasOriginProject;
    private Set<String> allDevs;

    @FXML
    private Label activityNameLabel, timeSpanLabel, remainingHoursLabel, timeBudgetErrorLabel, activityNameErrorLabel;

    @FXML
    private AnchorPane viewActivityPane, editActivityPane;

    @FXML
    private ListView<String> assignedDevsListView, editAssignedDevsListView, editUnassignedDevsListView;
    private final ObservableList<String> assignedDevsList = FXCollections.observableArrayList();
    private final ObservableList<String> assignedDevs = FXCollections.observableArrayList(), unassignedDevs = FXCollections.observableArrayList();

    @FXML
    private TextField logHoursField, activityNameField, timeBudgetField;

    @FXML
    private DatePicker startDateField, endDateField;

    @FXML
    private Button logHoursButton, cancelButton, editActivityButton, cancelEditButton, saveChangesButton, deleteActivityButton;

    @FXML
    public void initialize() {

        cancelButton.setOnMouseClicked(this::cancelButton);
        cancelEditButton.setOnMouseClicked(this::cancelEditButton);
        logHoursButton.setOnMouseClicked(this::submitHoursLogged);
        editActivityButton.setOnMouseClicked(this::editActivityButton);
        saveChangesButton.setOnMouseClicked(this::saveChangesButton);
        deleteActivityButton.setOnMouseClicked(this::deleteActivityButton);
        setupAddRemoveOnClick();
        restrictLogHoursField();
        restrictTimeBudgetField();
        restrictDatePicker();

        assignedDevsListView.setItems(assignedDevsList);
        editAssignedDevsListView.setItems(assignedDevs);
        editUnassignedDevsListView.setItems(unassignedDevs);
        allDevs = SceneManager.getInstance().getAllUsers();
    }

    //SHOW ASSIGNED PEOPLE
    //ADD HOUR CONTRIBUTION - SHOULD SUBTRACT HOURS FROM REMAINING ACTIVITY


    public void setActivityOverviewScreen(Activity activity) {
        this.activity = activity;

        activityNameLabel.setText(activity.getName());
        timeSpanLabel.setText(formatDateRange(activity.getStartDate(), activity.getEndDate()));
        remainingHoursLabel.setText(Integer.toString(activity.getRemainingHours()));
        updateAssignedDevsListOverview();
        if (!activity.getDevelopersAssignedToActivity().contains(SceneManager.getInstance().getCurrentUser())) {
            logHoursButton.setDisable(true);
            logHoursField.setDisable(true);
        }
    }

    //Overloaded method because originProject is not always set.
    public void setActivityOverviewScreen(Activity activity, Project originProject) {
        setActivityOverviewScreen(activity);
        this.originProject = originProject;
        hasOriginProject = true;
    }


    public String formatDateRange(LocalDate startDate, LocalDate endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return startDate.format(formatter) + "  -  " + endDate.format(formatter);
    }




    public void cancelButton(MouseEvent click) {
        if (hasOriginProject) {
            mainScreenController.swapToProjectOverviewScreen(originProject);
        } else {
            mainScreenController.swapToMyActivitiesScreen(click);
        }
    }

    public void editActivityButton(MouseEvent click) {
        togglePage();
        setupEditPage();
    }

    public void cancelEditButton(MouseEvent click) {
        togglePage();
    }

    public void saveChangesButton(MouseEvent click) {
        activity.setBudgetHours(Integer.parseInt(timeBudgetField.getText()));

        activity.setStartDate(startDateField.getValue());
        activity.setEndDate(endDateField.getValue());

        activity.getDevelopersAssignedToActivity().removeAll(unassignedDevs);

        for (String addedDev : assignedDevs) {
            if (!activity.getDevelopersAssignedToActivity().contains(addedDev)) {
                activity.assignDeveloper(addedDev);
            }
        }

        setActivityOverviewScreen(activity);
        togglePage();
    }

    public void deleteActivityButton(MouseEvent click) {
        if (hasOriginProject) {
            originProject.getActivities().remove(activity);
        } else {
            for (Project proj : Schedule.getInstance().getProjects()) {
                proj.getActivities().remove(activity);
            }
        }
        mainScreenController.swapToProjectOverviewScreen(originProject);
    }

    private void togglePage() {
        //Inverts the boolean return value of the pane state and toggles.

        viewActivityPane.setDisable(!viewActivityPane.isDisable());
        viewActivityPane.setVisible(!viewActivityPane.isVisible());

        editActivityPane.setDisable(!editActivityPane.isDisable());
        editActivityPane.setVisible(!editActivityPane.isVisible());
    }

    private void restrictLogHoursField() {
        logHoursField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[0-9]*\\.?[0-9]*")) {
                logHoursField.setText(oldValue);
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

    private void submitHoursLogged(MouseEvent click) {
        int hoursToLog = Integer.parseInt(logHoursField.getText());
        if (hoursToLog == 0) {
            //Show error label or something
        } else if (hoursToLog > activity.getBudgetHours()) {
            //Show another error label maybe
        } else {
            activity.logHours(SceneManager.getInstance().getCurrentUser(), hoursToLog);
        }
        remainingHoursLabel.setText(Integer.toString(activity.getRemainingHours()));
    }

    private void updateAssignedDevsListOverview() {
        assignedDevsList.setAll(activity.getDevelopersAssignedToActivity());
    }

    private void setupEditPage() {
        activityNameField.setText(activity.getName());
        timeBudgetField.setText(Integer.toString(activity.getBudgetHours()));
        startDateField.setValue(activity.getStartDate());
        endDateField.setValue(activity.getEndDate());

        assignedDevs.setAll(activity.getDevelopersAssignedToActivity());
        unassignedDevs.setAll(allDevs);
        unassignedDevs.removeAll(assignedDevs);
    }

    private void updateDevsLists() {
        //Make a copied set of all the devs and remove the ones that are assigned. This gives us a list of the ones that are unassigned.
        Set<String> unassignedSet = new HashSet<>(allDevs);
        assignedDevs.forEach(unassignedSet::remove); //This is faster than removeAll because... Java?

        unassignedDevs.setAll(unassignedSet);
    }

    private void setupAddRemoveOnClick() {
        editUnassignedDevsListView.setOnMouseClicked(event -> {
            String selected = editUnassignedDevsListView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                assignedDevs.add(selected);
                updateDevsLists();
            }
        });

        editAssignedDevsListView.setOnMouseClicked(event -> {
            String selected = editAssignedDevsListView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                assignedDevs.remove(selected);
                updateDevsLists();
            }
        });
    }

    //This will NOT restrict the amount of assignable hours, since they can be contributed to by multiple people.
    //Of course, one *could* restrict the amount of assignable hours to the count of:
    //(Pseudocode) (totalDaysRemainingBeforeEndDate * 24) >= (assignedDevelopers * 24)
    //Higher workload than that would be quite impossible.
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
