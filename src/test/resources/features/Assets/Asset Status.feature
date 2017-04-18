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
    Given I am on the 'New Request' page using the following licence number '123555'
    And I complete the fulfilment request for 'Lewis - Series 8' and ProdID '1/5634/0030/31#001' with 'not required by date' selecting 'first asset'
    And I enter the following Licence Number '123555'
    And I can change the status from 'requested' to 'fulfilled' for ProdId '1/5634/0030/31#001' and 'Lewis - Series 8' and licence number '123555'
    Then the label status on the Overview page has changed to 'fulfilled' for ProdId '1/5634/0030/31#001' and 'Lewis - Series 8' and licence number '123555'
    And today's date is displayed for the 'Fulfilled' date


  Scenario: Asset status can be changed from 'Fulfilled' to 'Requested' by creating a new request
    Given I am on the 'New Request' page using the following licence number '123555'
    And Production ID '1/5634/0030/31#001' is checked as previously Requested for 'Lewis - Series 8' on the New request page
    When I complete the fulfilment request for 'Lewis - Series 8' and ProdID '1/5634/0030/31#001' with 'not required by date' selecting 'first asset'
    And I enter the following Licence Number '123555'
    Then the label status on the Overview page has changed to 'requested' for ProdId '1/5634/0030/31#001' and 'Lewis - Series 8' and licence number '123555'

  Scenario: '-' is displayed for a requested asset if no required by date is available
    Given I am on the 'New Request' page using the following licence number '127093'
    And I complete the fulfilment request for 'Tutankhamun' and ProdID '2/3150/0001#002' with 'not required by date' selecting 'first asset'
    When I enter the following Licence Number '127093'
   Then 'Required By date on the left Selection Details menu for Production ID '2/3150/0001#002' of 'Tutankhamun' is '-'