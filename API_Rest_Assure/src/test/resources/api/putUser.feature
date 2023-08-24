@api_only

Feature: PUT /user API endpoint features


  Scenario: Update the user entry

    Given the request is authenticated with a valid API key
    And the request "Content-type" header is set to "application/json"
    And the request "id" query parameter is set to "67"
    And the request body is set to the following payload as map
      | username  | bdsjhfsdfjdsbjf            |
      | firstName | myusernameExampleFirst     |
      | lastName  | myusernameExampleLast      |
      | email     | myusernameExample@mail.com |

    When I send a "PUT" request to the endpoint "/user"
    Then the response log should be displayed
    Then the response status code should be 200
    And the response "Content-Type" header should be "application/json"
    And the response time should be less than 1000 ms
    And the response body should have "message" field with value "User updated successfully"


  @api_test
  Scenario: Update the user entry negative

    Given the request is authenticated with a valid API key
    And the request "Content-type" header is set to "application/json"
    And the request "id" query parameter is set to "67"
    And the request body is set to the following payload as map
      | username  | bdsjhfsdfjdsbjf            |
      | firstName | myusernameExampleFirst     |
      | lastName  | myusernameExampleLast      |

    When I send a "PUT" request to the endpoint "/user"
    Then the response log should be displayed
    Then the response status code should be 422
    And the response "Content-Type" header should be "application/json"
    And the response time should be less than 1000 ms
    And the response body should have "message" field with value "Missing or Invalid Required Fields!"
    And the response body should have "fields" field with values
        |   username  |
        |   firstName  |
        |   lastName  |
        |   email  |




