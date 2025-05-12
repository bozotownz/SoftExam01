package dtu;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class ActivityTileView {

    @FXML
    private Label activityNameLabel, assignedHrsLabel;

    @FXML
    private AnchorPane activityTilePane;

    private SubpageController subpageController;

    private Activity activity;

    private Project originProject;
    private boolean hasOriginProject = false;

    @FXML
    public void initialize() {

    }

    public void setupTile(Activity activity, SubpageController subpageController) {
        this.activity = activity;
        this.subpageController = subpageController;
        activityNameLabel.setText(activity.getName());
        assignedHrsLabel.setText(activity.getBudgetHours() + " Hrs");
        if (activity.getRemainingHours() == 0) {
            activityTilePane.setOpacity(0.7);
        }
        activityTilePane.setOnMouseClicked(this::openActivityOverviewScreen);
    }

    public void setOriginProject(Project originProject) {
        this.originProject = originProject;
        hasOriginProject = true;
    }

    //This is such a bad way of doing it. The proper way is to use a stack and simply pop it to return to the previous screen.
    public void openActivityOverviewScreen(MouseEvent click) {
        if (hasOriginProject) {
            subpageController.mainScreenController.swapToActivityOverviewScreenFromProject(activity, originProject);
        } else {
            subpageController.mainScreenController.swapToActivityOverviewScreen(activity);
        }
    }


}
