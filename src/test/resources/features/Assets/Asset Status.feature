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
    And 'Fulfilled' date on the left Selection Details menu for Production ID '1/5634/0030#002' of 'Lewis - Series 8' is 'today's date'

  Scenario: Asset status can be changed from 'Fulfilled' to 'Requested' by creating a new request
    Given I am on the 'New Request' page using the following licence number '123555'
    And Production ID '1/5634/0030#002' is checked as previously Requested for 'Lewis - Series 8' on the New request page
    When I complete the fulfilment request for 'Lewis - Series 8' and ProdID '1/5634/0030#002' with 'not required by date' selecting 'first asset'
    And I enter the following Licence Number '123555'
    Then the label status on the Overview page has changed to 'requested' for ProdId '1/5634/0030#002' and 'Lewis - Series 8' and licence number '123555'


#  Scenario: Multiple assets can be set to 'Fulfilled' at once
#    Given I am on the 'New Request' page using the following licence number '123333'
#    And I complete the fulfilment request for 'Vera - Series 2' and ProdID '1/7314/0007#002,1/7314/0009#002,1/7314/0010#002,1/7314/0008#002' with 'not required by date' selecting 'first asset'
#    When I enter the following Licence Number '123333'
#    Then I can set the status to 'Fulfilled' for multiple assets '1/7314/0007#002,1/7314/0008#002' of 'Vera - Series 2'
#    And 'Fulfilled' date on the left Selection Details menu for Production ID '1/7314/0008#002' of 'Vera - Series 2' is 'today's date'
#
#  Scenario: Multiple assets can be set to 'External Requested' at once
#    Given I enter the following Licence Number '123665'
#    When I can set the status to 'Requested' for multiple assets '1/9946/0001#001,1/9946/0004#001' of 'Hebrides: Islands on the Edge'
#    Then 'Requested' date on the left Selection Details menu for Production ID '1/9946/0001#001' of 'Hebrides: Islands on the Edge' is 'today's date'