@api_only
@API
Feature: PATCH /user API endpoint features

  @testing_api
  Scenario: Update the user entry

    Given the request is authenticated with a valid API key
    And the request "Content-type" header is set to "application/json"
    And the request "id" query parameter is set to "747"
    And the request body is set to the following payload as pojo for patch
      | firstName | Sierra55    |
      | lastName  | Dowell55    |
  When I send a "PATCH" request to the endpoint "/user"
  Then the response log should be displayed
  Then the response status code should be 200
  And the response "Content-Type" header should be "application/json"
  And the response time should be less than 1000 ms
  And the response body should have "message" field with value "User updated successfully"





