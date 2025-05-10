Feature: Timeline Management
As a project leader
I want to modify activity timelines
So that project schedules remain accurate

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


Scenario: Modify activity timeline
    Given an activity "Tempo" exists with dates "2026-10-15" to "2026-10-31"
    When I change the end date to "2026-11-07"
    Then the activity timeline should be updated