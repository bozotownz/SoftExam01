#Feature: Timeline Management
#As a project leader
#I want to modify activity timelines
#So that project schedules remain accurate
#
#Scenario: Modify activity timeline
#    Given an activity "Design" exists with dates "2022-10-15" to "2022-10-31"
#    When I change the end date to "2022-11-07"
#    Then the activity timeline should be updated
#    And assigned developers should be notified