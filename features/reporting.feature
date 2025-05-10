Feature: Reporting
As a project leader
I want to generate time reports
So that resource usage is clear

Background:
    Given there exists no projects
    And the user creates a project named "Project Alpha"
    Then the system should generate a project number in the format 25001
    Given a project with projectID 25001 exists
    When I assign a project leader named "huba"
    Then The system should assign "huba" to the project "Project Alpha"
    When I create a new activity "Tempo" in the project with projectID 25001
    And set a budget of 60 hours
    And set the start date to "2026-10-15" and end date to "2026-10-31"
    Then the activity should be added to the project
    And I assign developer "loni" to activity "Tempo"
    Given I am logged in as developer "loni"
    When I enter 13 hours for "Tempo"

Scenario: Generate report
    Given I am logged in as a project leader "huba"
    And project "25001" has recorded hours
    When I generate a report
    Then it lists all hours spent