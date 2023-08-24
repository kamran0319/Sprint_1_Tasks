@api_only

Feature: GET /user API endpoint features


  Scenario: Retrieve a single user with valid id

    Given the request is authenticated with a valid API key
    And the request "Content-type" header is set to "application/json"
    And the request "id" query parameter is set to "47"
    When I send a "GET" request to the endpoint "/user"
    Then the response log should be displayed
    Then the response status code should be 200
    And the response "Content-Type" header should be "application/json"
    And the response time should be less than 1000 ms
    And the response body should have "id" field with value "47"
    And the response body should have "username" field with value "SherlockHolmes"
    And the response body should have "firstName" field with value "Duotech"
    And the response body should have "lastName" field with value "Batch"
    And the response body should have "email" field with value "Dtacademy20@gmail.com"
    And the response body should have "createdAt" field with value "2022-09-28 00:00:00"
