package dtu;

public abstract class SubpageController {

    //Protected such that inheritors can access it.
    protected MainScreenController mainScreenController;

    public void setMainController(MainScreenController mainScreenController) {
        this.mainScreenController = mainScreenController;
    }
}
