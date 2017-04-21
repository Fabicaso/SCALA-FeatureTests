@licenceStatus
Feature: License Status

  As a GE ops user
  I want to check the Licence Status and Status notices
  So that I can act accordingly

  Background:
    Given I am on the 'Login' page
    When I login with the following valid credentials
    Then the 'Current Requests' page is displayed

  Scenario Outline: Licence Status and Licence Notice for different Material Delivery ids (PR, BP, HP, HF, XX)
    When I enter the following Licence Number '<licence number>'
    Then the '<licence status>' is displayed on the Overview Page
    And the '<Status notices>' is displayed correctly on the Overview Page
    Examples:
      | licence number | licence status | Status notices          |
      | 120513         | Abandoned      | Part Payment Required   |
      | 44900          | Cancelled      | Fulfilment Not Required |
      | 123625         | Draft          | Payment Not Required    |
      | 105738         | Issued         | Payment Not Required    |
      | 111677         | Syndicate      | Payment Not Required    |
      | 120737         | Signed         | Full Payment Required   |
      | 122147         | Draft          | Full payment required   |
      | 111757         | Draft          | Fulfilment Not Required |
      | 105738         | Issued         | Payment Not Required    |
      | 120794         | Issued         | Payment Received        |
      | 118952         | Issued         | Part payment required   |
      | 111677         | Syndicate      | Payment Not Required    |
      | 123247         | Syndicate      | Fulfilment Not Required |
      | 119664         | Signed         | Pre-Sig Completed       |
      | 115759         | Signed         | Payment Received        |
      | 120737         | Signed         | Full Payment Required   |
