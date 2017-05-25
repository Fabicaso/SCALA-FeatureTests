@assetsstatus
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
    And I complete the fulfilment request for 'Lewis - Series 8' and ProdID '1/5634/0030#002' with 'not required by date' selecting 'first asset'
    And I enter the following Licence Number '123555'
    And I can change the status from 'Requested' to 'Fulfilled' for ProdId '1/5634/0030#002' and 'Lewis - Series 8' and licence number '123555'
    Then the label status on the Overview page has changed to 'Fulfilled' for ProdId '1/5634/0030#002' and 'Lewis - Series 8' and licence number '123555'
    And 'Fulfilled' date on the right Selection Details menu for Production ID '1/5634/0030#002' of 'Lewis - Series 8' is 'today's date'

  Scenario: Asset status can be changed from 'Fulfilled' to 'Requested' by creating a new request
    Given I am on the 'New Request' page using the following licence number '123555'
    And Production ID '1/5634/0030#002' is checked as previously Requested for 'Lewis - Series 8' on the New request page
    When I complete the fulfilment request for 'Lewis - Series 8' and ProdID '1/5634/0030#002' with 'not required by date' selecting 'first asset'
    And I enter the following Licence Number '123555'
    Then the label status on the Overview page has changed to 'Requested' for ProdId '1/5634/0030#002' and 'Lewis - Series 8' and licence number '123555'

  Scenario: Production status can be changed from 'Requested' to 'Fulfilled' on the 'Overview' page  for External fulfilment
    Given I enter the following Licence Number '123888'
    And I can change the status from 'Outstanding' to 'Requested' for ProdId '1/5634/0036/37#001' and 'Lewis - Series 9' and licence number '123888'
    When I enter the following Licence Number '123888'
    And the Asset source is set to 'External' for ProdId '1/5634/0036/37#001' series 'Lewis - Series 9' and licence number '123888'
    Then I can change the status from 'Requested' to 'Fulfilled' for ProdId '1/5634/0036/37#001' and 'Lewis - Series 9' and licence number '123888'
    And the pie chart is correctly updated for external fulfilment

  Scenario: Production status can be changed from 'Fulfilled' to 'Requested' on the 'Overview' page for External fulfilment
    Given I enter the following Licence Number '123888'
    And I am able to change the status from 'Outstanding' to 'Requested' for ProdId '2/1229/0010#002' and 'Endeavour - Series 3' and licence number '123888'
    And I enter the following Licence Number '123888'
    And the Asset source is set to 'External' for ProdId '2/1229/0010#002' series 'Endeavour - Series 3' and licence number '123888'
    And I am able to change the status from 'Requested' to 'Fulfilled' for ProdId '2/1229/0010#002' and 'Endeavour - Series 3' and licence number '123888'
    When I enter the following Licence Number '123888'
    And the Asset source is set to 'External' for ProdId '2/1229/0010#002' series 'Endeavour - Series 3' and licence number '123888'

  Scenario: Production status can be changed from 'Requested' to 'Not Required' on the 'Overview' page
    Given I enter the following Licence Number '124366'
    And I am able to change the status from 'Outstanding' to 'Not Required' for ProdId '2/2113/0001#002' and 'Breathless' and licence number '124366'
    And the label status on the Overview page has changed to 'Not Required' for ProdId '2/2113/0001#002' and 'Breathless' and licence number '124366'

  Scenario: Production status can be changed from 'Not Required' to 'Requested' on the 'Overview' page
    Given I enter the following Licence Number '124366'
    When I can change the status from 'Not Required' to 'Requested' for ProdId '2/2113/0001#002' and 'Breathless' and licence number '124366'
    Then the label status on the Overview page has changed to 'Requested' for ProdId '2/2113/0001#002' and 'Breathless' and licence number '124366'

#  Scenario: Multiple assets can be set to 'Fulfilled' at once
#    Given I am on the 'New Request' page using the following licence number '123333'
#    And I complete the fulfilment request for 'Vera - Series 2' and ProdID '1/7314/0007#002,1/7314/0009#002,1/7314/0010#002,1/7314/0008#002' with 'not required by date' selecting 'first asset'
#    When I enter the following Licence Number '123333'
#    Then I can set the status to 'Fulfilled' for multiple assets '1/7314/0007#002,1/7314/0008#002' of 'Vera - Series 2' and licence number '123333'
#    And 'Fulfilled' date on the left Selection Details menu for Production ID '1/7314/0008#002' of 'Vera - Series 2' is 'today's date'

  Scenario: Production can be cancelled when it's in the state of Requests from the Overview Page
    Given I am on the 'New Request' page using the following licence number '122333'
    And I complete the fulfilment request for 'A Touch of Frost - Series 8' and ProdID 'Y/1774/0028' with 'not required by date' selecting 'first asset'
    When I enter the following Licence Number '122333'
    Then I can change the status from 'Requested' to 'Cancelled' for ProdId 'Y/1774/0028' and 'A Touch of Frost - Series 8' and licence number '122333'
    And the label status on the Overview page has changed to 'Outstanding' for ProdId 'Y/1774/0028' and 'A Touch of Frost - Series 8' and licence number '122333'

  Scenario: Production can be cancelled when it's in the state of Requests from the Current Requests Page
    Given I am on the 'New Request' page using the following licence number '122333'
    And I complete the fulfilment request for 'A Touch of Frost - Series 8' and ProdID 'Y/1774/0027' with 'not required by date' selecting 'first asset'
    When asset details with production id 'Y/1774/0027' and licence number '122333' are displayed on the 'Current Requests' page under 'No Required By Date' section
    Then I can change the status to 'Cancelled' on the Current Request page

#  Scenario: Production can be cancelled when it's in the state of In Progress //ToDo Create new test when InProgress Status is created

#  Scenario: Production can be cancelled when it's in the state of Delivered //ToDo Create new test when InProgress Status is created