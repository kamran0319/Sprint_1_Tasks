package stepDefinitions.api;

import io.cucumber.java.en.And;
import org.hamcrest.Matchers;
import stepDefinitions.SharedData;

import java.util.List;

public class PutUserStepDefs {

    SharedData sharedData;

    public PutUserStepDefs(SharedData sharedData) {
        this.sharedData = sharedData;
    }

    @And("the response body should have {string} field with values")
    public void theResponseBodyShouldHaveFieldWithValues(String key, List<String> list) {
        sharedData.getResponse().then().body(key, Matchers.equalTo(list));
    }


}
