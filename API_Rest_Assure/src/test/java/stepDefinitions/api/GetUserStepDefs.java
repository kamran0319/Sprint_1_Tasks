package stepDefinitions.api;

import io.cucumber.java.en.And;
import stepDefinitions.SharedData;

public class GetUserStepDefs {

    SharedData sharedData;

    public GetUserStepDefs(SharedData sharedData) {
        this.sharedData = sharedData;
    }

    @And("the request {string} query parameter is set to {string}")
    public void theRequestQueryParameterIsSetTo(String key, String value) {

        sharedData.getRequestSpecification().queryParam(key, value);
    }
}
