Feature: Developer Assignment
As a project leader
I want to assign and unassign developers
So that I can manage team workloads


Background:
    Given there exists no projects
    And the user creates a project named "Project Alpha"    
    Then the system should generate a project number in the format 25001
    Given a project named "Project Alpha" exists
    When I assign a project leader named "huba" 
    Then The system should assign "huba" to the project "Project Alpha"
    Given a project with projectID 25001 exists
    When I create a new activity "TempActivity" in the project with projectID 25001
    And set a budget of 50 hours
    And set the start date to "2025-11-01" and end date to "2025-11-14"
    Then the activity should be added to the project
    And I create a new activity "Testing" in the project with projectID 25001
    And set a budget of 50 hours
    And set the start date to "2025-10-01" and end date to "2025-10-14"
    Then the activity should be added to the project



Scenario: Assign a developer
    Given I "huba" logged in as a project leader for project 25001
    When I assign developer "DEI" to activity "TempActivity"
    Then the developer should be assigned

Scenario: Unassign a developer
    Given I "huba" logged in as a project leader for project 25001
    And I assign developer "DEI" to activity "TempActivity"
     When I remove the developer
     Then the system should remove the assignment
    #var nødt til at gøre det på denne måde 
    #Given developer "DEI" is assigned to activity "TempActivity"
    #When I remove the developer
    #Then the system should remove the assignment