package dtu;

import javafx.fxml.FXML;

public class ActivityOverviewScreenController {

    private Activity originActivity; //GET THIS FROM THE CLICKED ACTIVITY TILE

    @FXML
    public void initialize() {

    }

    //SHOW ASSIGNED PEOPLE
    //ADD HOUR CONTRIBUTION - SHOULD SUBTRACT HOURS FROM REMAINING ACTIVITY


    public void addDeveloper() {
        //Get selected developer(Most likely dropdown combobox)

        String developer = "placeholder";

        originActivity.assignDeveloper(developer);
    }
}
