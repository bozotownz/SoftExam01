package dtu;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class AllProjectsScreenController extends SubpageController {


    @FXML
    private Button createNewProjectButton;



    @FXML
    public void initialize() {
        createNewProjectButton.setOnMouseClicked(this::createNewProjectButton);
    }

    public void createNewProjectButton(MouseEvent click) {
        mainScreenController.swapToCreateNewProjectScreen();
    }

    public void addNewProjectTile(Project project) {
        //Generate another tile from fxml template
        //Link it to the specific project
        //Clickable, opens menu for editting project
        //Saves and sets new values once the project is closed.
    }
}
