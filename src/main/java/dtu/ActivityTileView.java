package dtu;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class ActivityTileView {

    @FXML
    private Label activityNameLabel, assignedHrsLabel;

    @FXML
    private AnchorPane activityTilePane;

    private SubpageController subpageController;

    private Activity activity;

    @FXML
    public void initialize() {

    }

    public void setupTile(Activity activity, SubpageController subpageController) {
        this.activity = activity;
        this.subpageController = subpageController;
        activityNameLabel.setText(activity.getName());
        assignedHrsLabel.setText(activity.getBudgetHours() + " Hrs");
    }


}
