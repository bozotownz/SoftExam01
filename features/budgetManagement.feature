Feature: Budget Management
As a project leader
I want to edit budgeted time for activities
So that budget allocations reflect actual project needs

Background:
    Given there exists no projects 
    And the user creates a project named "Project Alpha"
    Then the system should generate a project number in the format 25001
    Given a project with projectID 25001 exists
    When I create a new activity "Requirements Analysis" in the project with projectID 25001
    And set a budget of 100 hours
    And set the start date to "2025-10-01" and end date to "2025-10-14"
    Then the activity should be added to the project

Scenario: Edit budgeted time
    Given an activity "Requirements Analysis" has a budget of 100 hours
    When I update the budget to 75 hours
    Then the system should reflect the new budgeted time

Scenario: Prevent negative budget
    Given an activity "Requirements Analysis" has a budget of 100 hours
    When I update the budget to -10 hours
    Then the system should reject the change