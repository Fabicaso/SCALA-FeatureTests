@login
Feature: Login

  As a GE ops user
  I want to log into Craft
  So Craft is more secure

  @smoke
  Scenario: User can login sucessfully
    Given I am on the 'Login' page
    When I login with the following valid credentials
      |UserName|Password|
      |automationtestcraft@gmail.com|@utomationtestcraft|
    Then the 'Current Requests' page is displayed


