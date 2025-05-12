package dtu;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;


public class CSVHandler {

    //Method for loading from the files in the db folder - Split into different files like this to avoid 2d arrays saved to csv files
    public static Schedule loadScheduleFromCSV() throws IOException {
        Schedule schedule = Schedule.getInstance();

        //Load configuration
        loadConfig(schedule);
        
        //Load projects and build map
        Map<Integer, Project> projectMap = loadProjects(schedule);
        
        //Load activities
        loadActivities(projectMap);
        
        //Load developer assignments
        loadDeveloperAssignments(projectMap);
        
        //Load developer hours
        loadDeveloperHours(projectMap);
        
        return schedule;
    }
    
    //Config - Mainly for the projectiterator
    private static void loadConfig(Schedule schedule) throws IOException {
        File metadataFile = new File("./db/config.csv");
        
        try (BufferedReader br = new BufferedReader(new FileReader(metadataFile))) {
            br.readLine(); // Skips header - first line of csv files
            String line = br.readLine();
            if (line != null) {
                String[] values = line.split(",");
                schedule.setProjectIterator(Integer.parseInt(values[0]));
            }
        }
}
    //Projects loaded in as IDs, names and projectleaders (if any)
    private static Map<Integer, Project> loadProjects(Schedule schedule) throws IOException {
        Map<Integer, Project> projectMap = new HashMap<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader("./db/projects.csv"))) {
            br.readLine(); // Skips header - first line of csv files
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = parseCSVLine(line);
                if (values.length >= 2) {
                    int projectID = Integer.parseInt(values[0]);
                    String projectName = values[1];
                    String projectLeaderName = values.length > 2 ? values[2] : "";
                    
                    Project project = new Project(projectName, projectID);
                    if (!projectLeaderName.isEmpty()) {
                        project.setProjectLeader(projectLeaderName);
                    }
                    schedule.addProject(project);
                    projectMap.put(projectID, project);
                }
            }
        }
        
        return projectMap;
    }

    //Activities loaded into projects with project ID, activitynames, budgetted hours and start/enddate
    private static void loadActivities(Map<Integer, Project> projectMap) throws IOException {
        File activitiesFile = new File("./db/project_activities.csv");
        
        try (BufferedReader br = new BufferedReader(new FileReader(activitiesFile))) {
            br.readLine(); // Skips header - first line of csv files
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = parseCSVLine(line);
                if (values.length >= 5) {
                    int projectID = Integer.parseInt(values[0]);
                    String activityName = values[1];
                    int budgetHours = Integer.parseInt(values[2]);
                    LocalDate startDate = values[3].isEmpty() ? null : LocalDate.parse(values[3]);
                    LocalDate endDate = values[4].isEmpty() ? null : LocalDate.parse(values[4]);
                    
                    Activity activity = new Activity(activityName, budgetHours, startDate, endDate);
                    
                    Project project = projectMap.get(projectID);
                    if (project != null) {
                        project.addActivity(activity);
                    }
                }
            }
        }
    }
    
    //Load developers assigned to activities
    private static void loadDeveloperAssignments(Map<Integer, Project> projectMap) throws IOException {
        File assignmentsFile = new File("./db/developer_assignments.csv");
        
        try (BufferedReader br = new BufferedReader(new FileReader(assignmentsFile))) {
            br.readLine(); // Skip header - first line of csv files
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = parseCSVLine(line);
                if (values.length >= 3) {
                    int projectID = Integer.parseInt(values[0]);
                    String activityName = values[1];
                    String developerName = values[2];
                    
                    Project project = projectMap.get(projectID);
                    if (project != null) {
                        Activity activity = project.findActivityByName(activityName);
                        if (activity != null) {
                            activity.assignDeveloper(developerName);
                        }
                    }
                }
            }
        }
    }

    //Loads hours that devs have assigned to the activities
    private static void loadDeveloperHours(Map<Integer, Project> projectMap) throws IOException {
        File hoursFile = new File("./db/dev_hours_log.csv");
        
        try (BufferedReader br = new BufferedReader(new FileReader(hoursFile))) {
            br.readLine(); // Skip header - first line of csv files
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = parseCSVLine(line);
                if (values.length >= 4) {
                    int projectID = Integer.parseInt(values[0]);
                    String activityName = values[1];
                    String developerName = values[2];
                    int hoursLogged = Integer.parseInt(values[3]);
                    
                    Project project = projectMap.get(projectID);
                    if (project != null) {
                        Activity activity = project.findActivityByName(activityName);
                        if (activity != null) {
                            activity.logHours(developerName, hoursLogged);
                        }
                    }
                }
            }
        }
    }

    //Used for the reading phase of the csv files
    private static String[] parseCSVLine(String line) {
        return line.split(",");
    }
    
}