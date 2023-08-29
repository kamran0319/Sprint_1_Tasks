package stepDefinitions.api;

import io.cucumber.java.en.And;
import stepDefinitions.SharedData;

public class GetPlaylistsStepDefs {

    SharedData sharedData;

    public GetPlaylistsStepDefs(SharedData sharedData) {
        this.sharedData = sharedData;
    }



    @And("the JWT token is set in the header")
    public void theJWTTokenIsSetInTheHeader() {

        sharedData.getRequestSpecification().header("Authorization", sharedData.getJWTToken());
    }

}
