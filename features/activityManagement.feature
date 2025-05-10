Feature: Activity Management
As a user
I want to manage activities within projects
So that project tasks are clearly defined and organized

Background: 
    Given there exists no projects 
    And the user creates a project named "Project Alpha"
    Then the system should generate a project number in the format 25001

Scenario: Create a new activity
    Given a project with projectID 25001 exists
    When I create a new activity "Requirements Analysis" in the project with projectID 25001
    And set a budget of 100 hours
    And set the start date to "2025-10-01" and end date to "2025-10-14"
    Then the activity should be added to the project

#denne scenario er irrelevant ikke?
#Scenario: Schedule future activity
#    Given I am logged in as a project leader
#    When I create an activity "Implementation"
#    And I set the start date to "2025-06-01" and end date to "2025-07-01"
#    Then the activity should be created with specified dates