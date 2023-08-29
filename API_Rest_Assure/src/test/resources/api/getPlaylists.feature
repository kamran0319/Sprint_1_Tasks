@api_only
@API
Feature: GET /playlists API endpoint features

Background:
  Given the request is authenticated with a valid API key
  And the request "Content-type" header is set to "application/json"
  And the request body is set to the following payload as map
    | username  | duotech2023            |
    | password | duotech     |
  When I send a "POST" request to the endpoint "/login"
  Then the response log should be displayed
  Then the response status code should be 200
  And the response access token is stored


  Scenario: Get playlists for a specific user

    Given the request specification is reset
    Given the request is authenticated with a valid API key
    And the request "Content-type" header is set to "application/json"
    And the JWT token is set in the header
    When I send a "GET" request to the endpoint "/playlists"
    Then the response log should be displayed
    Then the response status code should be 200
    And the response "Content-Type" header should be "application/json; charset=UTF-8"
    And the response time should be less than 1000 ms








