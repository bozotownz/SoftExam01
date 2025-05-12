package dtu;

import java.time.Year;
import java.util.ArrayList;

public class Schedule {
    private static Schedule instance;
    private ArrayList<Project> projects = new ArrayList<>();
    private String respondText;
    //Iterator of projects - change this depending on the csv files loaded.
    private int projectIterator = 1; 

    //Constructs a Schedule as a recurring instance if it doesn't already exist. Singleton state
    public static Schedule getInstance() {
        if (instance == null) {
            instance = new Schedule();
        }
        return instance;
    }

    //Adds project to the list of projects in the schedule
    public void addProject(Project project) {
        projects.add(project);
        changeRespondsText("added", project.getProjectName());
    }

    public int getProjectIterator() {
    return projectIterator;
    }

    //Sets the project iterator
    public void setProjectIterator(int projectIterator) {
    this.projectIterator = projectIterator;
    }

    //Creates a project
    public Project createProject(String projectName) {
        int projectID = (Year.now().getValue()-2000)*1000 + projectIterator;
        projectIterator++;
        while(projectExistsID(projectID)) {
            projectID++;
            projectIterator++;
        }
        Project project = new Project(projectName, projectID);
        return project;
    }

    public ArrayList<Project> getProjects() {
        return projects;
    }

    //Does a project exist with this name, inside the list of projects
    public boolean projectExistsName(String string) {
        for (int i = 0; i < projects.size(); i++) {
            if (projects.get(i).getProjectName().equals(string)) {
                return true;
            }
        }
        return false;
    }

    //Does a project exist with this ID, inside the list of projects
    public boolean projectExistsID(int projectID) {
        assert projectID >= 0;
        assert projects != null;
        for (int i = 0; i < projects.size(); i++) {             //1
            if (projects.get(i).getProjectID() == projectID) {  //2
                assert projects.get(i).getProjectID() == projectID;
                return true;                                    //3
            }
        }
        return false;                                           //4
    }

    //Returns the project if found
    public Project findProjectByID(int projectID) {
        for (int i = 0; i < projects.size(); i++) {
            if (projects.get(i).getProjectID() == projectID) {
                return projects.get(i);
            }
        }
        throw new IllegalArgumentException("Project with ID '" + projectID + "' not found");     
    }

    //Returns project by name if found
    public Project findProjectByName(String string) {
        for (int i = 0; i < projects.size(); i++) {
            if (projects.get(i).getProjectName().equals(string)) {
                return projects.get(i);
            }
        }
        throw new IllegalArgumentException("Project with name '" + string + "' not found");
    }
        
    //Delete the project by name
    public boolean removeProject(String removedProject) {
        for (int i = 0; i < projects.size(); i++) {
            if (projects.get(i).getProjectName().equals(removedProject)) {
                projects.remove(projects.get(i));
                changeRespondsText("removed", removedProject);
                return true;
            }
        }
        return false;
    }

    //Delete the specific project from the project list
    public boolean deleteProjectByRef(Project project) {
        if (project != null) {
            projects.remove(project);
            return true;
        }
        return false;
    }

    //You added/removed a project: (the given projectName)
    public void changeRespondsText(String string, String projectName) {
        
        respondText = "You " + string + " a Project: " + projectName;
    }

    public String getRespondText() {
        return respondText;
    }

    //Reset for the projectIterator (when year changes)
    public void reset() {
        projects.clear();
        projectIterator = 1;
    }


    
}
