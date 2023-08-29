package stepDefinitions.api;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import stepDefinitions.SharedData;
import utils.ConfigReader;

import java.util.List;

import static org.hamcrest.Matchers.*;

public class GetUsersStepDefs {

    SharedData sharedData;

    public GetUsersStepDefs(SharedData sharedData) {
        this.sharedData = sharedData;
    }


    @Given("the request is authenticated with a valid API key")
    public void the_request_is_authenticated_with_a_valid_api_key() {
        sharedData.getRequestSpecification().
                                queryParam("api_key", ConfigReader.getProperty("api.key.duotify"));


    }
    @Given("the request {string} header is set to {string}")
    public void the_header_is_set_to(String key, String value) {
        sharedData.getRequestSpecification().header(key, value);

    }
    @When("I send a {string} request to the endpoint {string}")
    public void i_send_a_request_to_the_endpoint(String method, String endpoint) {
        switch (method) {
            case "GET" -> sharedData.setResponse(sharedData.getRequestSpecification().when().log().all().get(endpoint));
            case "POST" ->
                    sharedData.setResponse(sharedData.getRequestSpecification().when().log().all().post(endpoint));
            case "PUT" -> sharedData.setResponse(sharedData.getRequestSpecification().when().log().all().put(endpoint));
            case "PATCH" ->
                    sharedData.setResponse(sharedData.getRequestSpecification().when().log().all().patch(endpoint));
            case "DELETE" ->
                    sharedData.setResponse(sharedData.getRequestSpecification().when().log().all().delete(endpoint));
            default -> throw new IllegalArgumentException(method + ": This request method is invalid.");
        }

    }
    @Then("the response log should be displayed")
    public void the_response_log_should_be_displayed() {
        sharedData.getResponse().then().log().all();
    }
    @Then("the response status code should be {int}")
    public void the_response_status_code_should_be(Integer statusCode) {
        sharedData.getResponse().then().statusCode(statusCode);
    }
    @Then("the response {string} header should be {string}")
    public void the_response_header_should_be(String key, String value) {
        sharedData.getResponse().then().header(key, value);
    }
    @Then("the response time should be less than {long} ms")
    public void the_response_time_should_be_less_than_ms(Long ms) {
         sharedData.getResponse().then().time(lessThan(ms));
    }
    @Then("the users amount should be {int}")
    public void the_users_amount_should_be(int amount) {
//        List<Map<String, Object>> users  = sharedData.getResponse().then().
//                extract().path("$");
//
//        Assert.assertEquals(amount, users.size());

        sharedData.getResponse().then().body("$", hasSize(amount));
    }
    @Then("the response should contain a list of all users with the following fields")
    public void the_response_should_contain_a_list_of_all_users_with_the_following_fields(List<String> expectedKeys) {

//        List<Map<String, Object>> list = sharedData.getResponse().then().extract().path("$");
//
//        for (Map<String, Object> eachUser : list) {
//            Assert.assertEquals(new HashSet<>(expectedKeys), eachUser.keySet());
//        }

        for(String key : expectedKeys){
            sharedData.getResponse().then().body("[0]", hasKey(key));
        }

    }

    @Then("the response body should have {string} field with value {string}")
    public void theResponseBodyShouldHaveFieldWithValue(String key, String value) {
        sharedData.getResponse().then().body(key, equalTo(value));
    }

    @Given("the request is not authenticated with a valid API key")
    public void theRequestIsNotAuthenticatedWithAValidAPIKey() {

        sharedData.getRequestSpecification().
                queryParam("api_key", "invalidKey");
    }



}
