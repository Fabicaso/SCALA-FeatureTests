Feature: Create a new fulfilment order

  As a GE Ops user
  I would like to create a new fulfilment order
  So that I can send it to Deluxe for fulfilment

  Scenario: Navigate to new fulfilment order page from a licence
    Given I have navigated to the new fulfilment request page
    And I have entered licence id "Create Ful Order-123"
    When I click to create the new work fulfilment request
    Then I am taken to the new fulfilment page
