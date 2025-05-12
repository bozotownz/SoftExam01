package dtu;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ActivityOverviewScreenController extends SubpageController {

    private Activity activity; //GET THIS FROM THE CLICKED ACTIVITY TILE
    private Project originProject; //REFERENCE TO SEND YOU BACK TO PROJ OVERVIEW IF NEEDED
    private boolean hasOriginProject;

    @FXML
    private Label activityNameLabel, timeSpanLabel;

    @FXML
    private Button showAssignedDevelopersButton, cancelButton;

    @FXML
    public void initialize() {
        showAssignedDevelopersButton.setOnMouseClicked(this::getAssignedDevelopers);
        cancelButton.setOnMouseClicked(this::cancelButton);
    }

    //SHOW ASSIGNED PEOPLE
    //ADD HOUR CONTRIBUTION - SHOULD SUBTRACT HOURS FROM REMAINING ACTIVITY


    public void addDeveloper() {
        //Get selected developer(Most likely dropdown combobox)

        String developer = "placeholder";

        activity.assignDeveloper(developer);
    }

    public void setActivityOverviewScreen(Activity activity) {
        this.activity = activity;
        activityNameLabel.setText(activity.getName());
        timeSpanLabel.setText(formatDateRange(activity.getStartDate(), activity.getEndDate()));
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


    public void getAssignedDevelopers(MouseEvent click) {
        for (String user : activity.getDevelopersAssignedToActivity()) {
            System.out.println(user);
        }
    }

    public void cancelButton(MouseEvent click) {
        if (hasOriginProject) {
            mainScreenController.swapToProjectOverviewScreen(originProject);
        } else {
            mainScreenController.swapToMyActivitiesScreen(click);
        }
    }
}
