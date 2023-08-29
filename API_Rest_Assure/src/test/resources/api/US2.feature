Feature: Demo


@API
  Scenario:US2.Api endpoint
    Given The request is authenticated with a valid API key
    And The request "Content-type" header is set to "application/json"
    And The request "id" query parameter is set to "45"
    When I send "GET" request to endpoint "/user"
    Then The response log should be displayed
    Then The status code should be 200
    And Response time should be less than 500 ms


  @API
  Scenario:US2.Api endpoint negativ API key
    Given The request is authenticated with an Invalid API key
    And The request "Content-type" header is set to "application/json"
    And The request "id" query parameter is set to "45"
    When I send "GET" request to endpoint "/user"
    Then The status code should be 401
    And Response time should be less than 500 ms
    Then The response body should have "message" field with value "Invalid or missing API Key. Provide a valid api key with 'api_key' query parameter."

  @API
  Scenario:US2.Api endpoint User id is not provided or invalid
    Given The request is authenticated with a valid API key
    And The request "Content-type" header is set to "application/json"
    And The request "id" query parameter is set to ""
    When I send "GET" request to endpoint "/user"
    Then The response log should be displayed
    Then The status code should be 400
    And Response time should be less than 500 ms
    Then The response body should have "message" field with value "Bad request. User id is not provided."

  @API
  Scenario:US2.Api endpoint user ID is valid but not found in the database
    Given The request is authenticated with a valid API key
    And The request "Content-type" header is set to "application/json"
    And The request "id" query parameter is set to 1
    When I send "GET" request to endpoint "/user"
    Then The response log should be displayed
    Then The status code should be 404
    And Response time should be less than 500 ms
    Then The response body should have "message" field with value "User not found."

  @API
  Scenario:US2.Api endpoint  user's information in JSON format with the following properties
    Given The request is authenticated with a valid API key
    And The request "Content-type" header is set to "application/json"
    And The request "id" query parameter is set to "45"
    When I send "GET" request to endpoint "/user"
    Then The response log should be displayed
    Then The status code should be 200
    And Response time should be less than 500 ms
    And The response body should have field with values
      | id          |
      | first_name  |
      | last_name   |
      | email       |
      | created_at  |
      | modified_at |
      | type        |
      | active      |



