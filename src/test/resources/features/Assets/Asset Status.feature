@assets
Feature: Asset Status

  As a GE ops user
  I want to see the status of the assets
  So that I can keep a track of what client has received

  Background:
    Given I am on the 'Login' page
    When I login with the following valid credentials
    Then the 'Current Requests' page is displayed


  Scenario: Asset status can be changed from 'requested' to 'fulfilled' and fulfilled date is displayed
    When I am on the 'New Request' page using the following licence number '123555'
    And I complete the fulfilment request for '1/5634/0030/31#001' with 'not required by date' selecting 'first asset'
    And I enter the following Licence Number '123555'
    Then I can change the status from 'requested' to 'fulfilled' for ProdId '1/5634/0030/31#001' and 'Lewis - Series 8'
    And today's date is displayed for the 'Fulfilled' date
