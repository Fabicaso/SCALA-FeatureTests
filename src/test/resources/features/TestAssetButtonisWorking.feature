Feature: Test that the Asset button is working on the New Fulfilment Request page

  As a GE Ops user
  I would like to select the Asset format
  So that I can send it to Deluxe for fulfilment

  Scenario: Navigate to new fulfilment order page from a licence
    Given I go to the the new fulfilment request page
    And I have enter the licence id "TESTAssetButton-123"
    When I click the Asset button
    Then I can select the asset format
