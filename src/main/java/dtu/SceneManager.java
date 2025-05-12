package dtu;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashSet;

public class SceneManager {

    private static SceneManager instance;

    private Stage stage;

    private Scene loginScene, mainScene;

    //Saving a ref for this so it can be manipulated.
    private LoginScreenController loginScreenController;

    private MainScreenController mainScreenController;

    private HashSet<String> allUsers;

    private String currentUser = "None";

    private SceneManager(Stage stage) {
        this.stage = stage;
        initLoginScreen();
        setAllUsers(loginScreenController.getAllUsers());
    }

    protected void initPostLogin() {
        initMainScreen();
        //And so on....
    }

    //This is used to initially instantiate the scenemanager with a reference to the stage. Doing it exactly like this isn't really needed, I'm reusing code here.
    //For example, one could feasibly also instantiate the inital stage here and have the ProjectApp class function as the launcher only.
    public static SceneManager getInstance(Stage stage) {
        if (instance == null) {
            instance = new SceneManager(stage);
        }
        return instance;
    }

    //Overloaded method since we want a way to get a reference to the manager without having to pass a stage as argument.
    //This throws an exception if its invoked before the scenemanager is initially constructed with a stage.
    public static SceneManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Call to getInstance in SceneManager before SceneManager was instantiated with stage parameter.");
        }
        return instance;
    }


    //Two examples for loading various screens. These will try to load a scene on launch and subsequently save a reference that can be reused.
    private void initLoginScreen() {
        try {
            FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("LoginScreen.fxml"));
            loginScene = new Scene(loginLoader.load());
            loginScreenController = loginLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    //For now I *might* just end up loading this on launch, but we want to load this and everything else AFTER the login has been passed.
    //Otherwise, how would we know which users data to put in the following screens?
    //Additionally, all screens should be wiped COMPLETELY if a user logs out for obvious safety reasons. 50-50
    private void initMainScreen() {
        try {
            FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("MainScreen.fxml"));
            mainScene = new Scene(mainLoader.load());
            mainScreenController = mainLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //These are called on the SceneManager in order to swap scenes whenever needed. This preserves the data even if a given scene is closed for a moment.
    //This is also why we would want to wipe the scenes once someone logs out, as it otherwise could be bypassed in order to view another users data.
    //(These things might not be needed at all, but i want to mention them regardless)
    public void swapToLoginScreen() {
        loginScreenController.resetPasswordField();
        stage.setScene(loginScene);
    }

    public void swapToMainScreen() {
        stage.setScene(mainScene);
    }

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }

    public String getCurrentUser() {
        return currentUser;
    }

    public HashSet<String> getAllUsers() {
        return allUsers;
    }

    private void setAllUsers(HashSet<String> users) {
        allUsers = users;
    }

}
