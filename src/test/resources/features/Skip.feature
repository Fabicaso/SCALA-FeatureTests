Feature: Skip All Assets

  As a GE Ops user
  I would like to skip an Asset
  So that I can NOT send it to Deluxe for fulfilment

  Scenario: Navigate to new fulfilment order page from a licence and hit the Skip All asset button
    Given I open the new fulfilment request page
    And I have typed in the licence id "LIC-123"
    When I click on skip all button
    Then All Assets should be Skipped