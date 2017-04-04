@licence
Feature: License Check

  As a GE ops user
  I want to enter the licence number
  So that I can check the list of productions

  Background:
    Given I am on the 'Login' page
    When I login with the following valid credentials
    Then the 'Current Requests' page is displayed

  Scenario: Invalid license number should give a warning message: "Could not find licence with id LicenceId"
    When I am on the 'New Request' page using the following licence number 999999999
    Then the 'Invalid Licence Error Msg' is displayed 'Could not find licence with id LicenceId(999999999)'


