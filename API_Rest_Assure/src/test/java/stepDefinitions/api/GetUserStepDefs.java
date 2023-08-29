package stepDefinitions.api;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import stepDefinitions.SharedData;

import static io.restassured.RestAssured.given;

public class GetUserStepDefs {

    SharedData sharedData;

    public GetUserStepDefs(SharedData sharedData) {
        this.sharedData = sharedData;
    }

    @And("the request {string} query parameter is set to {string}")
    public void theRequestQueryParameterIsSetTo(String key, String value) {

        sharedData.getRequestSpecification().queryParam(key, value);
    }
    @Given("the request specification is reset")
    public void theRequestSpecificationIsReset() {

        sharedData.setRequestSpecification(given());
    }
}
