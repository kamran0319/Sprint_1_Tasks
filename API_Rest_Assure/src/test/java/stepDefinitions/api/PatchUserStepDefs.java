package stepDefinitions.api;

import io.cucumber.java.en.And;
import org.hamcrest.Matchers;
import pojos.User;
import stepDefinitions.SharedData;

import java.util.List;
import java.util.Map;

public class PatchUserStepDefs {

    SharedData sharedData;

    public PatchUserStepDefs(SharedData sharedData) {
        this.sharedData = sharedData;
    }

    @And("the request body is set to the following payload as pojo for patch")
    public void theRequestBodyIsSetToTheFollowingPayloadAsPojoForPatch(Map<String, String> map) {

        sharedData.getRequestSpecification().body(
                User.builder().
                firstName(map.get("firstName")).
                lastName(map.get("lastName")).build());



    }
}
