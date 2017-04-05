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
    When I enter the following Licence Number '999999999'
    Then the 'Invalid Licence Error Msg' is displayed 'Could not find licence with id LicenceId(999999999)'

  Scenario: License number without any productions should give a warning message: 'No productions found for this licence ID'
    When I enter the following Licence Number '44900'
    Then the 'No productions found for this licence ID' is displayed

  Scenario: No Assets found msg is displayed on the New Request Page
    When I am on the 'New Request' page using the following licence number '123555'
    Then No assets found msg is displayed for the production id - '1/5634/0034#002'

  Scenario: License Start date is displayed
    When I enter the following Licence Number '123555'
    Then the 'start date' is displayed on Overview page

  Scenario: Non GI or Ventures Licence
    When I enter the following Licence Number '380'
    Then The 'Create New Request' is disabled
