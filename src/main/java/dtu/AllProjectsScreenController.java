package dtu;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;

import java.io.IOException;

public class AllProjectsScreenController extends SubpageController {


    @FXML
    private Button createNewProjectButton;

    @FXML
    private FlowPane projectFlowPane;



    @FXML
    public void initialize() {
        createNewProjectButton.setOnMouseClicked(this::createNewProjectButton);

        setupProjectTiles();
    }

    public void createNewProjectButton(MouseEvent click) {
        mainScreenController.swapToCreateNewProjectScreen();
    }

    public void setupProjectTiles() {
        Schedule schedule = Schedule.getInstance();
        if (!schedule.getProjects().isEmpty()) {
            for (Project project : schedule.getProjects()) {
                addNewProjectTile(project);
            }
        }
    }

    public void addNewProjectTile(Project project) {
        try {
            FXMLLoader tileLoader = new FXMLLoader(getClass().getResource("ProjectTileView.fxml"));
            Parent tile = tileLoader.load();
            ProjectTileView projectTileView = tileLoader.getController();
            projectTileView.setupTile(project, this);
            projectFlowPane.getChildren().add(tile);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Attempted to load empty schedule or failed to load project data. This should never happen?");
        }

    }
}
