Feature: Project Management
As a project leader
I want to create and delete projects
So that projects can be effectively managed

Background: 
    Given there exists no projects 
    And the user creates a project named "Project Alpha"
    Then the system should generate a project number in the format 25001

Scenario: Create a new project
    When the user creates a project named "Time Tracking System"
    Then the system should generate a project number in the format 25002
    And I should receive a confirmation message "You added a Project: Time Tracking System"
    When the user creates a project named "Project Beta"
    Then the system should generate a project number in the format 25003
    And I should receive a confirmation message "You added a Project: Project Beta"

Scenario: Assign a project leader
    Given a project named "Project Alpha" exists
    When I assign a project leader named "huba" 
    Then The system should assign "huba" to the project "Project Alpha"

Scenario: Delete a project
    Given a project named "Project Alpha" exists
    When I delete project "Project Alpha"
    Then the system should remove the project and all associated data
    And I should receive a confirmation message "You removed a Project: Project Alpha"

