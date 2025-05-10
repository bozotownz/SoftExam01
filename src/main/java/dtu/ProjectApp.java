package dtu;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.io.IOException;

public class ProjectApp extends Application {

    //DO NOT RUN THE PROGRAM WITH THIS CLASS

    @Override
    public void start(Stage primaryStage) throws IOException {

        primaryStage.setTitle("Project Manager App");
        primaryStage.setResizable(false);

        SceneManager.getInstance(primaryStage).swapToLoginScreen();
        primaryStage.show();
    }

}