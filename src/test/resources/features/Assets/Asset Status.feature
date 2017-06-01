@assetsstatus
Feature: Asset Status

  As a GE ops user
  I want to see the status of the Assets
  So that I can keep a track of what client has received

  Background:
    Given I am on the 'Login' page
    When I login with the following valid credentials
    Then the 'Current Requests' page is displayed

  Scenario: Asset status can be changed from 'Not Required' to 'Requested' on the 'Overview' page and back to Requested
    Given I enter the following Licence Number '124366'
    And I am able to change the status from 'Outstanding' to 'Not Required' for ProdId '2/2113/0001#002' and 'Breathless' and licence number '124366'
    And the label status on the Overview page has changed to 'Not Required' for ProdId '2/2113/0001#002' and 'Breathless' and licence number '124366'
    When I can change the status from 'Not Required' to 'Requested' for ProdId '2/2113/0001#002' and 'Breathless' and licence number '124366'
    Then the label status on the Overview page has changed to 'Requested' for ProdId '2/2113/0001#002' and 'Breathless' and licence number '124366'

  Scenario: Asset can be cancelled when it's in the state of Requests from the Overview Page
    Given I am on the 'New Request' page using the following licence number '122333'
    And I complete the fulfilment request for 'A Touch of Frost - Series 8' and ProdID 'Y/1774/0028' with 'not required by date' selecting 'first asset'
    When I enter the following Licence Number '122333'
    Then I can change the status from 'Requested' to 'Cancelled' for ProdId 'Y/1774/0028' and 'A Touch of Frost - Series 8' and licence number '122333'
    And the label status on the Overview page has changed to 'Outstanding' for ProdId 'Y/1774/0028' and 'A Touch of Frost - Series 8' and licence number '122333'

  Scenario: Asset can be cancelled when it's in the state of Requests from the Current Requests Page
    Given I am on the 'New Request' page using the following licence number '122333'
    And I complete the fulfilment request for 'A Touch of Frost - Series 8' and ProdID 'Y/1774/0027' with 'not required by date' selecting 'first asset'
    When asset details with production id 'Y/1774/0027' and licence number '122333' are displayed on the 'Current Requests' page under 'No Required By Date' section
    Then I can change the status to 'cancelled' on the Current Request page for Production Id 'Y/1774/0027' and licence id '122333' under 'No Required By Date' section

  Scenario: Asset can be cancelled when it's in the state of In Progress on the 'Overview' page
    Given I enter the following Licence Number '128002'
    And I am able to change the status from 'Outstanding' to 'Requested' for ProdId '2/1229/0014#003' and 'Endeavour - Series 4' and licence number '128002'
    When I am able to change the status from 'Requested' to 'In Progress' for ProdId '2/1229/0014#003' and 'Endeavour - Series 4' and licence number '128002'
    Then I can change the status from 'In Progress' to 'Cancelled' for ProdId '2/1229/0014#003' and 'Endeavour - Series 4' and licence number '128002'

  Scenario: Asset can be cancelled when it's in the state of Delivered on the 'Overview' page
    Given I enter the following Licence Number '128002'
    And I am able to change the status from 'Outstanding' to 'Requested' for ProdId '2/1229/0015#003' and 'Endeavour - Series 4' and licence number '128002'
    When I am able to change the status from 'Requested' to 'In Progress' for ProdId '2/1229/0015#003' and 'Endeavour - Series 4' and licence number '128002'
    And I am able to change the status from 'In Progress' to 'Delivered' for ProdId '2/1229/0015#003' and 'Endeavour - Series 4' and licence number '128002'
    Then I can change the status from 'Delivered' to 'Cancelled' for ProdId '2/1229/0015#003' and 'Endeavour - Series 4' and licence number '128002'

  Scenario: Asset status can be changed from 'Fulfilled' to 'Requested' on the 'Overview' page for External fulfilment
    Given I enter the following Licence Number '123888'
    And I set to External Fulfilled the status of ProdId '2/1229/0013#002' and 'Endeavour - Series 3' and licence number '123888'
    Then the Asset source is set to 'External' for ProdId '2/1229/0013#002' series 'Endeavour - Series 3' and licence number '123888'
    And I am able to change the status from 'Fulfilled' to 'Requested' for ProdId '2/1229/0013#002' and 'Endeavour - Series 3' and licence number '123888'

  Scenario: Asset status can be changed from 'Outstanding' to 'Fulfilled'' on the 'Current Request' page for External fulfilment
    Given I am on the 'New Request' page using the following licence number '123888'
    And I complete the fulfilment request for 'Endeavour - Series 3' and ProdID '2/1229/0011#002' with 'not required by date' selecting 'first asset'
    When asset details with production id '2/1229/0011#002' and licence number '123888' are displayed on the 'Current Requests' page under 'No Required By Date' section
    Then I can change the status to 'inProgress' on the Current Request page for Production Id '2/1229/0011#002' and licence id '123888' under 'No Required By Date' section
    And I can change the status to 'delivered' on the Current Request page for Production Id '2/1229/0011#002' and licence id '123888' under 'No Required By Date' section
    And I enter the following Licence Number '123888'
    And I can change the status from 'Delivered' to 'Fulfilled' for ProdId '2/1229/0011#002' and 'Endeavour - Series 3' and licence number '123888'

  Scenario: Asset status can be changed from 'Outstanding' to 'Fulfilled' on the 'Overview' page
    Given I am on the 'New Request' page using the following licence number '123888'
    And I complete the fulfilment request for 'Endeavour - Series 3' and ProdID '2/1229/0012#002' with 'not required by date' selecting 'first asset'
    When I enter the following Licence Number '123888'
    And I am able to change the status from 'Requested' to 'In Progress' for ProdId '2/1229/0012#002' and 'Endeavour - Series 3' and licence number '123888'
    And I am able to change the status from 'In Progress' to 'Delivered' for ProdId '2/1229/0012#002' and 'Endeavour - Series 3' and licence number '123888'
    Then I enter the following Licence Number '123888'
    And I can change the status from 'Delivered' to 'Fulfilled' for ProdId '2/1229/0012#002' and 'Endeavour - Series 3' and licence number '123888'

