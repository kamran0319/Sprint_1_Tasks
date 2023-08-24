@api_only

Feature: GET /users API endpoint features


  Scenario: Retrieve all users successfully

    Given the request is authenticated with a valid API key
    And the request "Content-type" header is set to "application/json"
    When I send a "GET" request to the endpoint "/users"
    Then the response log should be displayed
    Then the response status code should be 200
    And the response "Content-Type" header should be "application/json"
    And the response time should be less than 1000 ms
    And the users amount should be 2901
    And the response should contain a list of all users with the following fields
      | id         |
      | username   |
      | firstName  |
      | lastName   |
      | email      |
      | password   |
      | signUpDate |
      | profilePic |


    Scenario: Invalid api key

      Given the request is not authenticated with a valid API key
      And the request "Content-type" header is set to "application/json"
      When I send a "GET" request to the endpoint "/users"
      Then the response log should be displayed
      Then the response status code should be 401
      Then the response body should have "message" field with value "Invalid or missing API Key. API key must be provided as an 'api_key' query parameter."


  Scenario: Invalid method type

    Given the request is authenticated with a valid API key
    And the request "Content-type" header is set to "application/json"
    When I send a "POST" request to the endpoint "/users"
    Then the response log should be displayed
    Then the response status code should be 405
    Then the response body should have "message" field with value "Invalid request method"