package stepDefinitions.api;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import stepDefinitions.SharedData;
import utils.ConfigReader;

import java.util.List;

import static org.hamcrest.Matchers.*;

public class US2StepDefs {


    SharedData sharedData;

    public US2StepDefs(SharedData sharedData) {
        this.sharedData = sharedData;
    }


    @When("I send {string} request to endpoint {string}")
    public void i_send_a_request_to_endpoint(String requestMethod, String endpoint) {
        sharedData.setResponse(sharedData.getRequestSpecification().when().log().all().get(endpoint));
    }

    @Then("The response log should be displayed")
    public void the_response_log_should_be_displayed() {
        sharedData.getResponse().then().log().all();
    }

    @Given("The request is authenticated with a valid API key")
    public void the_request_is_authenticated_with_a_valid_api_key() {
        sharedData.getRequestSpecification().
                queryParam("api_key", ConfigReader.getProperty("api.key.morgage"));
    }

    @Given("The request is authenticated with an Invalid API key")
    public void the_request_is_authenticated_with_an_invalid_api_key() {
        sharedData.getRequestSpecification().
                queryParam("api_key", "invalidApiKey");
    }

    @Given("The request {string} header is set to {string}")
    public void the_request_header_is_set_to(String key, String value) {
        sharedData.getRequestSpecification().header(key, value);
    }

    @And("The request {string} query parameter is set to {string}")
    public void theRequestQueryParameterIsSetTo(String key, String value) {

        sharedData.getRequestSpecification().queryParam(key, value);
    }

    @Then("The status code should be {int}")
    public void the_status_code_should_be(Integer statusCode) {
        sharedData.getResponse().then().statusCode(statusCode);
    }

    @Then("Response time should be less than {long} ms")
    public void response_time_should_be_less_than_ms(Long time) {
        sharedData.getResponse().then().log().all().
                assertThat().
                time(lessThan(time));
    }

    @Then("The response body should have {string} field with value {string}")
    public void theResponseBodyShouldHaveFieldWithValue(String key, String value) {
        sharedData.getResponse().then().body(key, equalTo(value));
    }

    @And("The request {string} query parameter is set to {int}")
    public void theRequestQueryParameterIsSetTo(String key, int value) {

        sharedData.getRequestSpecification().queryParam(key, value);
    }

    @And("The response body should have field with values")
    public void theResponseBodyShouldHaveFieldWithValues(List<String> list) {
        for (String key : list) {
            sharedData.getResponse().then().body("", hasKey(key));
        }
    }
}