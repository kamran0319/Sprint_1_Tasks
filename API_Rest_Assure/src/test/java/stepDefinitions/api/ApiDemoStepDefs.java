package stepDefinitions.api;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import stepDefinitions.SharedData;

import static org.hamcrest.Matchers.lessThan;

public class ApiDemoStepDefs {


     SharedData sharedData;

    public ApiDemoStepDefs(SharedData sharedData) {
        this.sharedData = sharedData;
    }





    @Given("{string} header is set to {string}")
    public void header_is_set_to(String key, String value) {
        RestAssured.baseURI = "https://api.github.com";

        sharedData.getRequestSpecification().
              header(key, value);

    }
    @Given("{string} path Parameter is set to {string}")
    public void path_parameter_is_set_to(String key, String value) {
        sharedData.getRequestSpecification().pathParam(key, value);

    }
    @Given("{string} query Parameter is set to {string}")
    public void query_parameter_is_set_to(String key, String value) {
        sharedData.getRequestSpecification().queryParam(key, value);
    }


    @When("I send a {string} request to endpoint {string}")
    public void i_send_a_request_to_endpoint(String requestMethod, String endpoint) {
        sharedData.setResponse(sharedData.getRequestSpecification().when().log().all().get(endpoint));


    }
    @Then("the status code should be {int}")
    public void the_status_code_should_be(Integer status) {
        sharedData.getResponse().then(). log().all().
                assertThat().
                statusCode((status));

    }
    @Then("response time should be less than {long} ms")
    public void response_time_should_be_less_than_ms(Long time) {
        sharedData.getResponse().then(). log().all().
                assertThat().
                time(lessThan(time));
    }
}
