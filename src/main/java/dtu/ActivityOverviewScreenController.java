package dtu;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ActivityOverviewScreenController extends SubpageController {

    private Activity activity; //GET THIS FROM THE CLICKED ACTIVITY TILE

    @FXML
    private Label activityNameLabel, timeSpanLabel;

    @FXML
    public void initialize() {

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


    public String formatDateRange(LocalDate startDate, LocalDate endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return startDate.format(formatter) + "  -  " + endDate.format(formatter);
    }
}
