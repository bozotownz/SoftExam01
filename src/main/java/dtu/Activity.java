package dtu;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Activity {
    
    private final String activityName;
    private int budgetHours;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<String> assignedDevelopersActivity = new ArrayList<>();

    //Fuld constructor, med samtlige felter udfyldt fra start
    public Activity(String actName, int budgetHours, LocalDate starDate, LocalDate endDate) {
        this.activityName = actName;
        this.budgetHours = budgetHours;
        this.startDate = starDate;
        this.endDate = endDate;
    }

    //Constructor kun med navn (alt det andet sættes op senere)
    public Activity(String actName) {
        this.activityName = actName;
    }

    public String getName() {
        return activityName;
    }

    public int getBudgetHours() {
        return budgetHours;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setBudgetHours(int hours) {
        this.budgetHours = hours;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    //Her assignes en dev til en aktivitet
    public void assignDeveloper(String developerName) {
        if (!assignedDevelopersActivity.contains(developerName)) {
            assignedDevelopersActivity.add(developerName);
        }
    }

    //Her fjerness en dev fra en aktivitet
    public void removeDeveloper(String developerName) {
        if (assignedDevelopersActivity.contains(developerName)) {
            assignedDevelopersActivity.remove(developerName);
        }
    }

    //Fremtidig "hurtig" liste over udviklerer der er tildelt en aktivitet. Skal nok ikke bruges.
    public List<String> getDevelopersAssignedToActivity() {
        return assignedDevelopersActivity;
    }

    
}
