package dtu;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;


public class ProjectTileView {

    @FXML
    private Label projIDLabel, projNameLabel;

    @FXML
    private AnchorPane projectTilePane;

    private Project project;

    private AllProjectsScreenController allProjectsScreenController;


    @FXML
    public void initialize() {

    }


    public void setupTile(Project project, AllProjectsScreenController allProjectsScreenController) {
        this.allProjectsScreenController = allProjectsScreenController;
        this.project = project;
        projNameLabel.setText(project.getProjectName());
        projIDLabel.setText(Integer.toString(project.getProjectID()));
        projectTilePane.setOnMouseClicked(this::openEditProjectScreen);
    }


    //This is some ultra-cursed daisy chaining, but this avoids issues with mainscreencontroller not being fully instantiated when the project tiles are created.
    //This is because JavaFX does not finish instantiating a controller object fully before it finishes all statements in its standardized FXML-Initialize() function.
    public void openEditProjectScreen(MouseEvent click) {
        allProjectsScreenController.mainScreenController.swapToEditProjectScreen(project);
    }
}
