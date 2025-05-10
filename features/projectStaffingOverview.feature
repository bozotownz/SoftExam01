Feature: Project Staffing Overview
As a project leader
I want to view developer availability
So that I can effectively allocate team resources to activities

Background:
    Given there exists no projects
    And the user creates a project named "Project Alpha"
    Then the system should generate a project number in the format 25001
    Given a project with projectID 25001 exists
    When I assign a project leader named "huba"
    Then The system should assign "huba" to the project "Project Alpha"
    When I create a new activity "Design" in the project with projectID 25001
    And set a budget of 60 hours
    And set the start date to "2025-10-01" and end date to "2025-10-14"
    Then the activity should be added to the project
    And I assign developer "loni" to activity "Design"

Scenario: Check developer availability
    Given I am logged in as a project leader "huba"
    When I check the availability for week "2025-10-01" to "2025-10-07"
    Then the system should display a list of developers and their current activity load for that week
