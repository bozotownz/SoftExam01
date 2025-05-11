package dtu;

import java.time.Year;
import java.util.ArrayList;

public class Schedule {
    private static Schedule instance;
    private ArrayList<Project> projects = new ArrayList<>();

    private String respondText;
    private int projectIterator = 4; 


    public Schedule() {
    }

    public static Schedule getInstance() {
        if (instance == null) {
            instance = new Schedule();
        }
        return instance;
    }

    public void addProject(Project project) {
        projects.add(project);
        changeRespondsText("added", project.getProjectName());
    }

    public int getProjectIterator() {
    return projectIterator;
    }

    public void setProjectIterator(int projectIterator) {
    this.projectIterator = projectIterator;
    }

    //Given the current use of this, why does it even return a project rather than just try to add it?
    public Project createProject(String projectName) {
        int projectID = (Year.now().getValue()-2000)*1000 + projectIterator;
        projectIterator++;
        Project project = new Project(projectName, projectID);
        //addProject(project);
        return project;
    }

    public ArrayList<Project> getProjects() {
        return projects;
    }

    public boolean projectExistsName(String string) {
        for (int i = 0; i < projects.size(); i++) {
            if (projects.get(i).getProjectName().equals(string)) {
                return true;
            }
        }
        return false;
    }

    public boolean projectExistsID(int projectID) {

        for (int i = 0; i < projects.size(); i++) {
            if (projects.get(i).getProjectID() == projectID) {
                return true;
            }
        }
        return false;
    }


    public Project findProjectByID(int projectID) {
        for (int i = 0; i < projects.size(); i++) {
            if (projects.get(i).getProjectID() == projectID) {
                return projects.get(i);
            }
        }
        return projects.get(0);      
    }

    public Project findProjectByName(String string) {
        for (int i = 0; i < projects.size(); i++) {
            if (projects.get(i).getProjectName().equals(string)) {
                return projects.get(i);
            }
        }
        return projects.get(0);
    }
    

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

    public void changeRespondsText(String string, String projectName) {
        //you added/removed a project: (the given projectName)
        respondText = "You " + string + " a Project: " + projectName;
    }

    public String getRespondText() {
        return respondText;
    }

    /* public void reset() {
        projects.clear();
        projectIterator = 1;
    } */


    
}
