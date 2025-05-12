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

    //
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
    public Project createProjectOld(String projectName) {
        //Udkommentér denne til test - projectname bliver ikke carried over korrekt via features
        /* if (!projectName.matches("[a-zA-Z0-9\\\\s]+")) {
            throw new IllegalArgumentException("Project name must contain only letters, numbers and spaces");
        } */
        int projectID = (Year.now().getValue()-2000)*1000 + projectIterator;
        projectIterator++;
        Project project = new Project(projectName, projectID);
        //addProject(project);
        return project;
    }

    //Fix for projectIterator not being updated, old method is above if needed -M
    public Project createProject(String projectName) {
        //Udkommentér denne til test - projectname bliver ikke carried over korrekt via features
        /* if (!projectName.matches("[a-zA-Z0-9\\\\s]+")) {
            throw new IllegalArgumentException("Project name must contain only letters, numbers and spaces");
        } */
        int projectID = (Year.now().getValue()-2000)*1000 + projectIterator;
        projectIterator++;
        while(projectExistsID(projectID)) {
            projectID++;
            projectIterator++;
        }
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

    public boolean deleteProjectByRef(Project project) {
        if (project != null) {
            projects.remove(project);
            return true;
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

    public void reset() {
        projects.clear();
        projectIterator = 1;
    }


    
}
