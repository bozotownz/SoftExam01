package dtu;


public class Main {

    //This is the jumpstarter method for ProjectApp.
    //This is a TERRIBLE solution to a bug with maven dependencies and javafx.

    //This will throw an error on every run. I have no way to solve this since any implementation of module-info.java will break the junit test class due to project structure.
    //Cucumber needs to access the reflection in our src root, but the tests are outside of that and the test directory is not exportable via a mod-inf in the src.
    //This can be fixed in a multitude of ways:
    // -We can force open the tests dir and export them with a VM arg.
    // -We can implement the modinf file as normal and simply move the test class into our source.
    // -We can ignore the error
    // -We could rebuild the project structure


    public static void main(String[] args) {
        ProjectApp.launch(ProjectApp.class, args);
    }
}
