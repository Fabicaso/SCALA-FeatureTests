@display
Feature: Logo and General Display

 This feature will test that the
  Logo and General display area always shown correctly
  on the Craft website

  Background:
    Given I am on the 'Login' page
    When I login with the following valid credentials
    Then the 'Current Requests' page is displayed

  Scenario: Check that the ITV and Craft logo are displayed on the Craft website
    Then the 'ITV' logo is displayed
    And the 'Craft' logo is displayed

