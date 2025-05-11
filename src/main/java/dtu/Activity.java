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

    //Her fjerness en dev fra en aktivitet
    public void removeDeveloper(String developerName) {
        /* //pre
        assert developerName != null;       // assert 1
        List<String> initialAssignedDevelopers = new  ArrayList<>(assignedDevelopersActivity);  // assert 2

        if (assignedDevelopersActivity.contains(developerName)) {   // 1
            assignedDevelopersActivity.remove(developerName);   // 2
        //post 
        //condi 1
        assert !assignedDevelopersActivity.contains(developerName) || !initialAssignedDevelopers.contains(developerName);   // asert 3
        } else {
        // condit 2
        assert assignedDevelopersActivity.equals(initialAssignedDevelopers);    // assert 4
        }
        */
        // previous code if it messes with you
        
        if (assignedDevelopersActivity.contains(developerName)) {   // 1
            assignedDevelopersActivity.remove(developerName);   // 2
        }
         
    }

    //Fremtidig "hurtig" liste over udviklerer der er tildelt en aktivitet. Skal nok ikke bruges.
    public List<String> getDevelopersAssignedToActivity() { 
        return assignedDevelopersActivity;
    }

    public void logHours(String developerName, int hoursToLog) {
        assert developerName != null;   // assert 1 can comment out/delete if messing with fx
        assert hoursToLog >= 0; // assert 2 can comment out/delete if messing with fx

        int tempHours;
        if (developersLoggedHours.get(developerName) >= 0) {    // 1 
            tempHours = developersLoggedHours.get(developerName);   // 2
        } else {
            tempHours = 0;      // 3
        }

        int oldTotal = loggedBudgetHours;   // can comment out/delete if messing with fx
        int oldDeveloperHours = developersLoggedHours.getOrDefault(developerName, 0); // can comment out/delete if messing with fx
        
        developersLoggedHours.put(developerName, tempHours + hoursToLog);   // 4
        logHoursTotal(hoursToLog);      // 5

        assert developersLoggedHours.get(developerName) == oldDeveloperHours + hoursToLog;  // assert 3 can comment out/delete if messing with fx
        assert loggedBudgetHours == oldTotal + hoursToLog;  // assert 4 can comment out/delete if messing with fx
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
