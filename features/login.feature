Feature: Login
As a user i want to be able to log into the system

Scenario: a user from the list of workers tries to log in
    Given a user is not logged in
    And a user tries to log in with credentials "huba"
    Then the user succesfully log in

Scenario: a user from the list of workers tries to log in
    Given a user is not logged in
    And a user tries to log in with credentials "loni"
    Then the user succesfully log in

Scenario: a user not in the list of workers tries to log in
    Given a user is not logged in
    And a user tries to log in with credentials "abe"
    Then the user does not log in
    And the text changes to "Not Valid Credentials. Try Again."
