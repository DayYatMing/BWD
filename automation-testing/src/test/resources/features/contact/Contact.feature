Feature: Contact

  @contact
  Scenario: Contact Limited Details
    Given user is on login page
    When user navigates to and clicks Contacts menu
    Then user should be navigated to Contacts limited details page successfully

  @requiresLogin @contact
  Scenario: Contact Full Details
    Given user is on logged-in page
    When user navigates to and clicks Contacts menu
    Then user should be navigated to Contacts full details page successfully
