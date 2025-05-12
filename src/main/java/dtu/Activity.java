package dtu;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
// Asger 
public class Activity {
    
    private final String activityName;

    private LocalDate startDate;
    private LocalDate endDate;
    private List<String> assignedDevelopersActivity = new ArrayList<>();
    private Map<String, Integer> developersLoggedHours = new HashMap<>(); 
    //How many hours are planned for work
    private int budgetHours;
    //How many hours are actually spent on work
    private int loggedBudgetHours = 0;
    
    // Emil
    //Full constructor with all fields
    public Activity(String actName, int budgetHours, LocalDate startDate, LocalDate endDate) {
        if (actName == null || !actName.matches("[a-zA-Z0-9\\s]+")) {
            throw new IllegalArgumentException("Activity name must contain only letters, numbers, and spaces");
        }

        if (budgetHours < 0) {
            throw new IllegalArgumentException("Budget hours must be non-negative");
        }

        if (startDate != null && endDate != null && startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }

        this.activityName = actName;
        this.budgetHours = budgetHours;
        this.startDate = startDate;
        this.endDate = endDate;
    }
    // Mads
    //Partial constructor with only name required
    public Activity(String actName) {
        if (actName == null || !actName.matches("[a-zA-Z0-9\\s]+")) {
            throw new IllegalArgumentException("Activity name must contain only letters, numbers, and spaces");
        }
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
    // Asger
    public void setBudgetHours(int hours) {
        if (budgetHours < 0) {
            throw new IllegalArgumentException("Budget hours must be non-negative");
        }
        this.budgetHours = hours;
    }
    // Anton
    public void setStartDate(LocalDate startDate) {
        if (this.endDate != null && startDate != null && startDate.isAfter(this.endDate)) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }
        this.startDate = startDate;
    }
    // Niels
    public void setEndDate(LocalDate endDate) {
        if (this.startDate != null && endDate != null && endDate.isBefore(this.startDate)) {
            throw new IllegalArgumentException("End date cannot be before start date");
        }
        this.endDate = endDate;
    }
    //  Anton
    //Dev assigned to activity
    public void assignDeveloper(String developerName) {        
        assert developerName != null; // Precondition

        int originalSize = assignedDevelopersActivity.size();
        boolean alreadyAssigned = assignedDevelopersActivity.contains(developerName);   // 1

        if (!alreadyAssigned) {                                                         // 2
            assignedDevelopersActivity.add(developerName);                              // 3
            developersLoggedHours.put(developerName, 0);                          // 4
        }
        // post
        assert assignedDevelopersActivity.size() == originalSize ||                     //5
            assignedDevelopersActivity.size() == originalSize + 1;                      //6
    }

    // Emil
    //Dev removed from activity 
    public void removeDeveloper(String developerName) {
        // follwing code does not run with javafx. it does, however, work with tests.
        /* 
        assert developerName != null;       // assert 1
        List<String> initialAssignedDevelopers = new  ArrayList<>(assignedDevelopersActivity);  // assert 2

        if (assignedDevelopersActivity.contains(developerName)) {   // 1
            assignedDevelopersActivity.remove(developerName);   // 2
        //post 
        //condi 1
        assert !assignedDevelopersActivity.contains(developerName) || !initialAssignedDevelopers.contains(developerName);   // assert 3
        } else {
        // condit 2
        assert assignedDevelopersActivity.equals(initialAssignedDevelopers);    // assert 4
        }
        */

        // Working code for javafx
        if (assignedDevelopersActivity.contains(developerName)) {   // 1
            assignedDevelopersActivity.remove(developerName);   // 2
        }
         
    }

    public List<String> getDevelopersAssignedToActivity() { 
        return assignedDevelopersActivity;
    }

    //Asger 
    //logs hours of a developer to an activity.
    public void logHours(String developerName, int hoursToLog) {
        assert developerName != null;   // assert 1 can comment out/delete if messing with fx
        assert hoursToLog >= 0; // assert 2 can comment out/delete if messing with fx
        // set temphours to be 0 in case the if statement retuns anything other than a positive integer
        int tempHours = 0;
        if (developersLoggedHours.get(developerName) > 0) {
            tempHours = developersLoggedHours.get(developerName);
        }
        // DbC for assert later
        int oldTotal = loggedBudgetHours;   
        int oldDeveloperHours = developersLoggedHours.getOrDefault(developerName, 0); 
        //log hours for developer
        developersLoggedHours.put(developerName, tempHours + hoursToLog);   // 4
        //increment total logged hours for project
        logHoursTotal(hoursToLog);      // 5

        assert developersLoggedHours.get(developerName) == oldDeveloperHours + hoursToLog;  // assert 3 
        assert loggedBudgetHours == oldTotal + hoursToLog;  // assert 4 
    }
    // Niels
    // Changes the logged hours for a developer
    public void editLoggedHours(String developerName, int hoursToLog) {
        developersLoggedHours.put(developerName,hoursToLog);
    }
    //niels
    //Adds on top of logged hours for activity
    public void logHoursTotal(int hoursToLog) {
        loggedBudgetHours += hoursToLog;
    }
    //mads
    //Sets the logged hours directly instead of adding on top
    public void setLoggedBudgetHours(int hours) {
    this.loggedBudgetHours = hours;
    }
    //mads
    //Remaining hours of project for tracking
    public int getRemainingHours() {
        return Math.max(0, budgetHours - loggedBudgetHours);
    }

}
