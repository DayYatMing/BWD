Feature: Login

  @auth @login
  Scenario Outline: Successful login
    Given "<role>" is on login page
    When  "<role>" enters valid username and password
    And "<role>" enters OTP
    Then "<role>" should be logged in successfully

    Examples:
      | role  |
      | admin |
      | user  |


