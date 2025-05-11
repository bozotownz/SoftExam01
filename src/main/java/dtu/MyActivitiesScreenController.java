package dtu;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;



public class MyActivitiesScreenController extends SubpageController {


    @FXML
    private FlowPane myActivitiesFlowPane;


    @FXML
    public void initialize() {

        setupActivityTiles();
    }



    public void setupActivityTiles() {
        Schedule schedule = Schedule.getInstance();
        for (Project project : schedule.getProjects()) {
            for (Activity activity : project.getActivities()) {
                if (activity.getDevelopersAssignedToActivity().contains(SceneManager.getInstance().getCurrentUser())) {
                    addNewActivityTile(activity);
                }
            }
        }
    }



    public void addNewActivityTile(Activity activity) {
        try {
            FXMLLoader tileLoader = new FXMLLoader(getClass().getResource("ActivityTileView.fxml"));
            Parent tile = tileLoader.load();
            ActivityTileView activityTileView = tileLoader.getController();
            activityTileView.setupTile(activity, this);
            myActivitiesFlowPane.getChildren().add(tile);

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Attempted to load empty activity or failed to load activity data. This would probably happen given the awful structure of the data necessitating awful code.");
        }

    }


}
