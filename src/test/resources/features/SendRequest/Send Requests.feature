@sendRequest
Feature: Send Request page

  As a GE ops user
  I want to check that the 'Send Request' form is displayed correctly
  So that I can send an accurate request to Deluxe

  Background:
    Given I am on the 'Login' page
    When I login with the following valid credentials
    Then the 'Current Requests' page is displayed

  Scenario: Options for 'Frame Rate Output' are displayed correctly for 'Online' Delivery Medium and 'Framerate convert if needed' job
    Given I am on the 'Send Request' page using the following licence number '123156' series 'Blithe Spirit' and ProdID 'CFD0066/0001' selecting 'first asset'
    When I select 'Online' as the 'Delivery Medium' and 'FrameRate Convert If Needed' as the 'Job' on the 'Send Request'form
   Then the options for 'Frame Rate Output' are displayed correctly

  Scenario: Options for 'Frame Rate Output' are displayed correctly for 'Hard Drive' Delivery Medium and 'Framerate convert if needed' job
    Given I am on the 'Send Request' page using the following licence number '123156' series 'Blithe Spirit' and ProdID 'CFD0066/0001' selecting 'first asset'
    When I select 'Hard Drive' as the 'Delivery Medium' and 'FrameRate Convert If Needed' as the 'Job' on the 'Send Request'form
    Then the options for 'Frame Rate Output' are displayed correctly

  Scenario: text box 'Spec (Optional)' is displayed correctly for 'Online' Delivery Medium and 'Transcode if needed' job
    Given I am on the 'Send Request' page using the following licence number '123156' series 'Blithe Spirit' and ProdID 'CFD0066/0001' selecting 'first asset'
    When I select 'Online' as the 'Delivery Medium' and 'Transcode If Needed' as the 'Job' on the 'Send Request'form
    Then the options for 'Frame Rate Output' are displayed correctly
    And Resolution Outputs and Spec Optional text box are displayed

  Scenario: text box  'Spec (Optional)' is displayed correctly for 'Hard Drive' Delivery Medium and 'Transcode if needed' job
    Given I am on the 'Send Request' page using the following licence number '123156' series 'Blithe Spirit' and ProdID 'CFD0066/0001' selecting 'first asset'
    When I select 'Hard Drive' as the 'Delivery Medium' and 'Transcode If Needed' as the 'Job' on the 'Send Request'form
    Then the options for 'Frame Rate Output' are displayed correctly
    And Resolution Outputs and Spec Optional text box are displayed

