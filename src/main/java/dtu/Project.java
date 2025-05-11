package dtu;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Project {
    private int projectID;
    private String projectName;
    private ArrayList<Activity> activities =  new ArrayList<>();
    private String projectLeaderName;
    Map<String, Integer> developerActivityHashmap; 
    private Schedule schedule = Schedule.getInstance();
    private int projectTotalHours;

    //private Schedule schedule = new Schedule();

    public Project(String projectName, int projectID) {
        this.projectName = projectName;
        this.projectID = projectID;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getProjectLeader() {
        return projectLeaderName;
    }

    public void setProjectLeader(String projectLeaderName) {
        if (projectLeaderName != null && !projectLeaderName.matches("[a-zA-Z]+")) {
            throw new IllegalArgumentException("Project leader name must contain only letters");
        }
        this.projectLeaderName = projectLeaderName;
    }   

    public int getProjectID() {
        return projectID;
    }

    public void addActivity(Activity activity) {
        activities.add(activity);
    }

    public ArrayList<Activity> getActivities() {
        return activities;
    }


    public Map<String, Integer> getDeveloperActivityLoadForInterval(LocalDate searchStartDate, LocalDate searchEndDate) {
        developerActivityHashmap = new HashMap<>();
        
        // loop all activities
        for(Activity activity : activities) {
            // filter out activites that end before the searchStartDate and finish start after the searchEndDate
            if (activity.getStartDate().isAfter(searchEndDate) || activity.getEndDate().isBefore(searchEndDate)) {
                continue;
            }
            // add developers to hashmap
            for (String developer : activity.getDevelopersAssignedToActivity()) {
                int count = 0;
                if (developerActivityHashmap.containsKey(developer)) {
                    count = developerActivityHashmap.get(developer);
                }
                count++;
                developerActivityHashmap.put(developer, count);
            }
        }
        return developerActivityHashmap;
    }
    
    public Activity findActivityByName(String activityName) {
        assert activityName != null;                    //assert 1
        assert activities != null;                    //assert 2

        for (Activity a : activities) {                 //1
            if (a.getName().equals(activityName)) {     //2
                assert a != null;                       //assert 3
                return a;                               //3
            }
        }
        assert true;                                    //assert 3  4    
        return null;                                    //4
        /*
        for (Activity a : schedule.findProjectByID(projectID).getActivities()) {
            if (a.getName().equals(activtyName)) {
                activtyReturner = a;
            }
        }
        return activtyReturner;
        */
    }

    //Returnerer loggede timer
    public int getTotalProjectHours() {
        int hourCount = 0;
        for (Activity a : activities) {
            hourCount += a.getTotalHoursLogged();
        }
        return hourCount;
    }

    //Returnerer budgetterede timer
    public int getBudgetProjectHours() {
        int hourCount = 0;
        for (Activity a : activities) {
            hourCount += a.getBudgetHours();
        }
        return hourCount;
    }

    //Returnerer tilbageværende timer på et projekt
    public int getRemainingHours() {
        int remainingHours = (getBudgetProjectHours() - getTotalProjectHours());
        return remainingHours;
    }
}


