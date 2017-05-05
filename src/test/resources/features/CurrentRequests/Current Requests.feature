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
    Given I am on the 'New Request' page using the following licence number '123665'
    When I complete the fulfilment request for 'Desert Seas' and ProdID '2/1761/0001#001' with 'not required by date' selecting 'first asset'
    Then 'first asset' with production id '2/1761/0001#001' and licence number 123665 is displayed on the 'Current Requests' page under 'No Required By Date' section

  Scenario: Requested asset without a required by date details is displayed in the right side on the 'Current Requests' page
    Given I am on the 'New Request' page using the following licence number '123669'
    When I complete the fulfilment request for 'Miss Marple - Series 5' and ProdID '1/5576/0012#002' with 'not required by date' selecting 'first asset'
    Then asset details with production id '1/5576/0012#002' and licence number '123669' are displayed on the 'Current Requests' page under 'No Required By Date' section

  Scenario: Requested asset with required by date is displayed on the 'Current Requests' page
    Given I am on the 'New Request' page using the following licence number '123665'
    When I complete the fulfilment request for 'Harry at 30' and ProdID '2/2384/0001#002' with 'a required by date' selecting 'multiple assets'
    Then 'multiple assets' with production id '2/2384/0001#002' and licence number 123665 are displayed on the 'Current Requests' page under 'a required by date' section

  Scenario: Requested asset with required by date is displayed on the 'Current Requests' page for multiple Productions
    Given I am on the 'New Request' page using the following licence number '123667'
    When I complete the fulfilment request for 'Murdoch Mysteries - Series 4' and ProdID '1/6195/0044#001,1/6195/0045#001' with 'a required by date' selecting 'first asset'
    Then 'first asset' with production id '1/6195/0044#001,1/6195/0045#001' and licence number 123667 are displayed on the 'Current Requests' page under 'a required by date' section