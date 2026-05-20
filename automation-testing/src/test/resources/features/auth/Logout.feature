Feature: Logout

  @requiresLogin @auth @logout
  Scenario: Successful logout
    Given user is on logged-in page
    When user navigates to and clicks logout button
    Then user should be logged out successfully



