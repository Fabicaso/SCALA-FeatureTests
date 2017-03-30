@currentRequests
Feature: Current Requests

  As a Deluxe user
  I want to see a prioritised list of requests
  So I know the most important request I need to work on next

  Background:
    Given I am on the 'Login' page
    When I login with the following valid credentials
    Then the 'Current Requests' page is displayed

  Scenario: Requested asset without a required by date is displayed on the 'Current Requests' page
    Given I am on the 'New Request' page using the following licence number 123665
    When I complete the fulfilment request for '1: Desert Seas' of 'Desert Seas' with '2/1761/0001#001'
     #|Delivery Medium | Job | Resolution Output |
      #| Online | Transcode if needed | As per source |
    Then the current request for licence number 123665 is displayed on the 'Current Requests' page under 'No Required By Date' section

#  Scenario: Requested asset with required by date is displayed on the 'Current Requests' page
#    Given I am on the 'New Request' page using the following licence number
#      | 123665 |
#    When I complete the fulfilment request for '1: Desert Seas' of 'Desert Seas' with a required by date
#       #|Delivery Medium | Job | Resolution Output |
#      | Online | Framerate convert if needed | As per source |
#    Then the current request for licence number 123665 is displayed on the 'Current Requests' page under 'Required By Date' section
#
#  Scenario: Only requested assets are displayed on the 'Current Requests' page
#    Given I am on the 'New Request' page using the following licence number
#      | 123333 |
#    And I complete the fulfilment request for multiple assets of 'Vera - Series 2'
#     #|Delivery Medium | Job | Resolution Output |
#      | Online | Transcode | As per source |
#    And I am on the 'Overview' page using the following licence number
#      | 123333 |
#    And I change the status from 'Requested' to 'Fulfilled' for the first two assets of 'Vera - Series 2'
#    Then the total number of assets for the current request for licence number 123333 is displayed correctly
