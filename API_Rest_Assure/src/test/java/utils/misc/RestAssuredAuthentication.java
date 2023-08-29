package utils.misc;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.List;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class RestAssuredAuthentication {



    @Test
    public void basicAuth(){
        RestAssured.baseURI = "https://postman-echo.com";
        String username = "postman";
        String password = "password";

//        String base64Str = Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
//        System.out.println(base64Str);

        given().
                auth().basic(username, password).
//                header("Authorization" , "Basic " + base64Str).
        when().log().all().
                get("/basic-auth").
        then().log().all().
                statusCode(200).
                body("authenticated", Matchers.equalTo(true));
    }


    @Test
    public void apiKey(){
        RestAssured.baseURI = "http://duotify.us-east-2.elasticbeanstalk.com/api";


        given().

                queryParam("id" , "67").
                queryParam("api_key" , "e82042a5f58f449c9d5a9e3cf5a3f43b").
        when().log().all().
                get("/user").
                then().log().all().
                statusCode(200);
    }


    @Test
    public void bearerToken() {


        RestAssured.baseURI = "https://api.github.com";

        given().
                header("Accept", "application/vnd.github+json").
                header("Authorization", "Bearer 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").

                when().log().all().
                get("/user").

                then().log().all().
                assertThat().
                statusCode((200));

    }



    @Test
    public void JWTToken() {

        baseURI = "http://duotify.us-east-2.elasticbeanstalk.com/api";

        // Obtain the jwt token through /login endpoint
        String access_token = given().
                queryParam("api_key", "e82042a5f58f449c9d5a9e3cf5a3f43b").
                header("Accept", "application/json").
                header("Content-type", "application/json").
                body("{\n" +
                        "  \"username\": \"duotech2023\",\n" +
                        "  \"password\": \"duotech\"\n" +
                        "}").
                when().log().all().
                post("/login").
                then().log().all().statusCode(200).extract().path("access_token");

        // Send a request to get playlists that requires jwt token

        List<String> listOfPlaylists = given().
                queryParam("api_key", "e82042a5f58f449c9d5a9e3cf5a3f43b").
                header("Accept", "application/json").
                header("Authorization", access_token).

                when().log().all().
                get("/playlists").
                then().log().all().statusCode(200).extract().path("playlists.name");

        System.out.println(listOfPlaylists);
        //
        //


    }


    @Test
    public void Oauth2() {


        // Obtain the code
        //obtain the access token from the authorization server

      String token =   given().
                queryParam("client_id", "99ba68a587572004a35d").
                queryParam("client_secret", "6d2e3a005c39e530cf3a5582d1ae8554ca9ec301").
                queryParam("code", "cf753683cfa6884aee86"). // temporary code needs to be obtained every time
                header("Accept", "application/json").
         when().log().all().
                post("https://github.com/login/oauth/access_token").

         then().log().all().
                statusCode(200).extract().path("access_token");


        // Sent a request to the API endpoint using the access token

           given().
                   header("Authorization", "Bearer " + token).
           when().log().all().
                   get("https://api.github.com/user").
                then().log().all().statusCode(200);
    }


}
