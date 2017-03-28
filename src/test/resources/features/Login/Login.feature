@login
Feature: Login

  As a GE ops user
  I want to log into Craft
  So Craft is more secure


  Scenario: User can login sucessfully
    Given I am on the 'Login' page
    When I login with the following valid credentials
    Then the 'Current Requests' page is displayed




