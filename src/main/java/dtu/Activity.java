package dtu;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Activity {
    
    private final String activityName;
    private int budgetHours;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<String> assignedDevelopersActivity = new ArrayList<>();
    private int loggedBudgetHours = 0;
    private Map<String, Integer> developersLoggedHours = new HashMap<>(); 

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

    public int getTotalHoursLogged() {
        return loggedBudgetHours;
    }
    
    public int getTotalHoursLoggedForDeveloper(String developerName) {
        return developersLoggedHours.get(developerName);
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
            developersLoggedHours.put(developerName, 0);
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

    public void logHours(String developerName, int hoursToLog) {
        int tempHours;
        if (developersLoggedHours.get(developerName) >= 0) {
            tempHours = developersLoggedHours.get(developerName);
        } else {
            tempHours = 0;
        }
        developersLoggedHours.put(developerName, tempHours + hoursToLog);
        logHoursTotal(hoursToLog);
    }

     public void editLoggedHours(String developerName, int hoursToLog) {
        developersLoggedHours.put(developerName,hoursToLog);
    }

    public void logHoursTotal(int hoursToLog) {
        loggedBudgetHours += hoursToLog;
    }

    public void setLoggedBudgetHours(int hours) {
    this.loggedBudgetHours = hours;
    }

}
