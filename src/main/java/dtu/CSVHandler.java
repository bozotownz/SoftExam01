package dtu;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class CSVHandler {
    
    //Method for loading from the files in the db folder - Done like this to avoid 2d arrays saved to csv files
    public static Schedule loadScheduleFromCSV() throws IOException {
        Schedule schedule = Schedule.getInstance();
        schedule.reset();

        // Load metadata
        loadConfig(schedule);
        
        // Load projects and build map
        Map<Integer, Project> projectMap = loadProjects(schedule);
        
        // Load activities
        loadActivities(projectMap);
        
        // Load developer assignments
        loadDeveloperAssignments(projectMap);
        
        // Load developer hours
        loadDeveloperHours(projectMap);
        
        return schedule;
    }
    
    //Config - Mainly for the projectiterator
    private static void loadConfig(Schedule schedule) throws IOException {
        File metadataFile = new File("./db/config.csv");
        
        try (BufferedReader br = new BufferedReader(new FileReader(metadataFile))) {
            br.readLine(); // Skip header - first line of csv files
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
            br.readLine(); // Skip header - first line of csv files
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
        if (!activitiesFile.exists()) {
            return;
        }
        
        try (BufferedReader br = new BufferedReader(new FileReader(activitiesFile))) {
            br.readLine(); // Skip header - first line of csv files
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
    

    private static void loadDeveloperAssignments(Map<Integer, Project> projectMap) throws IOException {
        File assignmentsFile = new File("./db/developer_assignments.csv");
        if (!assignmentsFile.exists()) {
            return;
        }
        
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

    //USE THIS ONE AT YOUR OWN RISK :D not tested and might not be interesting enough to have active
    public static void saveScheduleToCSV(Schedule schedule) throws IOException {
        // Make sure the directory exists
        new File("./db").mkdirs();
        
        // Save metadata
        try (PrintWriter pw = new PrintWriter(new FileWriter("./db/config.csv"))) {
            pw.println("projectIterator,lastSaveDate");
            pw.println(schedule.getProjectIterator() + "," + LocalDate.now());
        }
        
        // Save projects
        try (PrintWriter pw = new PrintWriter(new FileWriter("./db/projects.csv"))) {
            pw.println("projectID,projectName,projectLeaderName");
            for (Project project : schedule.getProjects()) {
                pw.printf("%d,%s,%s%n", 
                    project.getProjectID(), 
                    escapeCSV(project.getProjectName()),
                    escapeCSV(project.getProjectLeader() != null ? project.getProjectLeader() : ""));
            }
        }
        
        // Save activities
        try (PrintWriter pw = new PrintWriter(new FileWriter("./db/activities.csv"))) {
            pw.println("projectID,activityName,budgetHours,startDate,endDate,loggedBudgetHours");
            for (Project project : schedule.getProjects()) {
                for (Activity activity : project.getActivities()) {
                    pw.printf("%d,%s,%d,%s,%s,%d%n",
                        project.getProjectID(),
                        escapeCSV(activity.getName()),
                        activity.getBudgetHours(),
                        activity.getStartDate() != null ? activity.getStartDate() : "",
                        activity.getEndDate() != null ? activity.getEndDate() : "",
                        activity.getTotalHoursLogged());
                }
            }
        }
        
        // Save developer assignments and hours
        saveDeveloperData(schedule);
    }

    //SAME AS ABOVE; IF WE WANT TO SAVE WE NEED IT - Otherwise can it
    private static void saveDeveloperData(Schedule schedule) throws IOException {
        try (PrintWriter pwAssign = new PrintWriter(new FileWriter("./db/developer_assignments.csv"));
            PrintWriter pwHours = new PrintWriter(new FileWriter("./db/developer_hours_log.csv"))) {
            
            pwAssign.println("projectID,activityName,developerName");
            pwHours.println("projectID,activityName,developerName,hoursLogged");
            
            for (Project project : schedule.getProjects()) {
                for (Activity activity : project.getActivities()) {
                    for (String developer : activity.getDevelopersAssignedToActivity()) {
                        pwAssign.printf("%d,%s,%s%n",
                            project.getProjectID(),
                            escapeCSV(activity.getName()),
                            escapeCSV(developer));
                        
                        int hours = activity.getTotalHoursLoggedForDeveloper(developer);
                        if (hours > 0) {
                            pwHours.printf("%d,%s,%s,%d%n",
                                project.getProjectID(),
                                escapeCSV(activity.getName()),
                                escapeCSV(developer),
                                hours);
                        }
                    }
                }
            }
        }
    }
    //Used for the writing phase of csv files - If any special characters are put into fields and saved it will crash.
    private static String escapeCSV(String value) {
        if (value == null) return "";
        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
    }

    //Used for the reading phase of the csv files
    private static String[] parseCSVLine(String line) {
        return line.split(",");
    }
    
}