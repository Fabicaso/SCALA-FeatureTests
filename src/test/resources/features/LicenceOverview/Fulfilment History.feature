@fulfilment
Feature: Fulfilment History

  As a GE ops user
  I want to know which productions under a licence have previously been fulfilled
  So that I have more information to determine if fulfilment is required

  Background:
    Given I am on the 'Login' page
    When I login with the following valid credentials
    Then the 'Current Requests' page is displayed


  Scenario: Fulfilment history is displayed on the first licence
    Given I am on the 'New Request' page using the following licence number '126077'
    And I complete the fulfilment request for 'Vera - Series 6' and ProdID '1/7314/0025#002' with 'today's date' selecting 'first asset'
    And I enter the following Licence Number '126077'
    And I am able to change the status from 'Requested' to 'Fulfilled' for ProdId '1/7314/0025#002' and 'Vera - Series 6' and licence number '126077'
    When I enter the following Licence Number '126631'
    And the Asset Status is 'Outstanding' for Series 'Vera - Series 6' and Production ID '1/7314/0025#002' and licence number '126631' on the Overview page
    Then 'Vera - Series 6' is flagged as Previous Fulfilled and the ProductionId '1/7314/0025#002' is flagged with a dot as Previous Fulfilled and licence '126631'
    And the previously fulfilled history details for Production id '1/7314/0025#002' of 'Vera - Series 6' and licence '126077' are displayed

