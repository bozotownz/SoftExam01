Feature: Time Logging
As a developer
I want to log my work hours
So that my work is accurately recorded

Background:
    Given there exists no projects
    And the user creates a project named "Project Alpha"
    Then the system should generate a project number in the format 25001
    Given a project with projectID 25001 exists
    When I assign a project leader named "loni"
    Then The system should assign "loni" to the project "Project Alpha"
    When I create a new activity "Bobs Activity" in the project with projectID 25001
    And set a budget of 60 hours
    And set the start date to "2025-11-13" and end date to "2025-11-20"
    Then the activity should be added to the project
    And I assign developer "mahu" to activity "Bobs Activity"

Scenario: Log work hours
    Given I am logged in as developer "mahu"
    When I enter 4 hours for "Bobs Activity"
    Then the system should record my entry