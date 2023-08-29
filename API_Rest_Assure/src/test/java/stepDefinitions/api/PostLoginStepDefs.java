package stepDefinitions.api;

import io.cucumber.java.en.And;
import stepDefinitions.SharedData;

public class PostLoginStepDefs {

    SharedData sharedData;

    public PostLoginStepDefs(SharedData sharedData) {
        this.sharedData = sharedData;
    }

    @And("the response access token is stored")
    public void theResponseAccessTokenIsStored() {
        String token = sharedData.getResponse().path("access_token");
        sharedData.setJWTToken(token);
    }
}
