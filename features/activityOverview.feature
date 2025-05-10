Feature: Activity Overview
As a user
I want to see assigned activities of a project
So I can manage my workload or my own activities

Background: 
    Given there exists no projects 
    And the user creates a project named "Project Alpha"
    Then the system should generate a project number in the format 25001
    Given a project with projectID 25001 exists
    When I create a new activity "Requirements Analysis" in the project with projectID 25001
    And set a budget of 100 hours
    And set the start date to "2025-10-01" and end date to "2025-10-14"
    Then the activity should be added to the project
    Given a project with projectID 25001 exists
    When I create a new activity "Two Requirements Analysis" in the project with projectID 25001
    And set a budget of 80 hours
    And set the start date to "2025-09-01" and end date to "2025-10-14"
    Then the activity should be added to the project
     And I assign developer "huba" to activity "Requirements Analysis"

Scenario: View tasks in certain project
    When I open activities in "Project Alpha"
    Then it should list "Requirements Analysis" and "Two Requirements Analysis"

Scenario: View personal activities
    Given im logged in as "huba"
    And I open my activities
    Then It should show "Requirements Analysis" 