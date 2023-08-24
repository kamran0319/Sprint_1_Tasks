package utils.misc;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import utils.ConfigReader;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class RestAssuredDemo {

    @BeforeClass
    public static void beforeClass(){
        RestAssured.baseURI = "https://api.github.com";
    }



    @Test
    public void basicDemo(){

        //https://api.github.com/v2/users/drgonzo21

        RestAssured.baseURI = "https://api.github.com";
//        RestAssured.basePath = "v1";

        // Chained syntax
               given().
                    header("Accept", "application/vnd.github+json").
                    pathParam("username", "drgonzo21"). // path params are added here
                    queryParam("pages","1").
                when(). log().all().  //log().all(). is used to display command line logs of the request or response content, added after given,when,then methods
//                    get("/users/drgonzo21").
                    get("/users/{username}").   // {username} will be replaced with the actual value in pathParams

                then(). log().all().
                     assertThat().
                     statusCode((200)).
                       time(lessThan(2000L)); // verify basic response time


        //Syntax with each section divided and stored in their own objects

        // given
        RequestSpecification requestSpecification = given().   // given() represents specifications of the request such as headers, parameters, body, authorization/authentication that are sent along with the request
                              header("Accept", "application/vnd.github+json");
//
        //when
        Response response = requestSpecification.when().log().all().  // when() represents the actual request method and endpoint where you are sending the request.
                                                                       // It returns a Response object which represents the returned response
                                                  get("/users/drgonzo21");

        //then
        response.then(). log().all().   // then() represents the assertions performed on the returned response such as status code, headers, body, etc
                statusCode(200).
                header("Server", "GitHub.com");


    }

    @Test
    public void demoPost(){

        String email = "duotechtest"+new Random().nextInt(1000) + "@gmail.com";
       //Test creation of the email
//
        given().
                header("Accept", "application/vnd.github+json").
                header("X-GitHub-Api-Version", "2022-11-28").
                header("Authorization", "Bearer " + ConfigReader.getProperty("api.token")).
                header("Content-Type", "application/json").
                body("{\n" +
                "    \"emails\" : [\""+email+"\"]\n" +
                "}").
        when(). log().all().
                post("/user/emails").

        then(). log().all().
                assertThat().
                statusCode(201).
                header("Content-Type", "application/json; charset=utf-8");

        // After the test cleanup by calling delete endpoint

        given().
                header("Accept", "application/vnd.github+json").
                header("X-GitHub-Api-Version", "2022-11-28").
                header("Authorization", "Bearer " + ConfigReader.getProperty("api.token")).
                header("Content-Type", "application/json").
                body("{\n" +
                        "    \"emails\" : [\""+email+"\"]\n" +
                        "}").
                when(). log().all().
                delete("/user/emails").

                then(). log().all().
                assertThat().
                statusCode(204).
                header("X-Accepted-OAuth-Scopes", "user");


    }

    @Test
    public void demoPatch(){


//
        given().
                header("Accept", "application/vnd.github+json").
                header("X-GitHub-Api-Version", "2022-11-28").
                header("Authorization", "Bearer " + ConfigReader.getProperty("api.token")).
                header("Content-Type", "application/json").
                body("{\n" +
                        "    \"visibility\" : \"public\"\n" +
                        "}").
                when(). log().all().
                patch("/user/email/visibility").

                then(). log().all().
                assertThat().
                statusCode(200).
                header("X-RateLimit-Limit", "5000");


    }


    @Test
    public void demoDelete(){

        String email = "duotechtest"+new Random().nextInt(1000) + "@gmail.com";
        // Create the email first
        given().
                header("Accept", "application/vnd.github+json").
                header("X-GitHub-Api-Version", "2022-11-28").
                header("Authorization", "Bearer " + ConfigReader.getProperty("api.token")).
                header("Content-Type", "application/json").
                body("{\n" +
                        "    \"emails\" : [\""+email+"\"]\n" +
                        "}").
                when(). log().all().
                post("/user/emails").

                then(). log().all().
                assertThat().
                statusCode(201).
                header("Content-Type", "application/json; charset=utf-8");

//    Delete the created email
        given().
                header("Accept", "application/vnd.github+json").
                header("X-GitHub-Api-Version", "2022-11-28").
                header("Authorization", "Bearer " + ConfigReader.getProperty("api.token")).
                header("Content-Type", "application/json").
                body("{\n" +
                        "    \"emails\" : [\""+email+"\"]\n" +
                        "}").
                when(). log().all().
                delete("/user/emails").

                then(). log().all().
                assertThat().
                statusCode(204).
                header("X-Accepted-OAuth-Scopes", "user");


    }



    @Test
    public void basicBodyValidationUsingPathMethod() {


        String username = "DrGonzo21";
     Response response =   given().
                header("Accept", "application/vnd.github+json").
                pathParam("username", username).
                when().log().all().
                get("/users/{username}").

                then().log().all().
                assertThat().
                statusCode((200)).
                body("login", equalTo(username)).extract().response();

              String login = response.path("login");
              System.out.println(login);

            Map<String, Object> entireResponse = response.path("$");
            System.out.println(entireResponse);








    }

    @Test
    public void basicBodyValidationUsingJsonPath() {


        String username = "DrGonzo21";
        JsonPath responseAsJsonPath =   given().
                header("Accept", "application/vnd.github+json").
                pathParam("username", username).
                when().log().all().
                get("/users/{username}").

                then().log().all().
                assertThat().
                statusCode((200)).
                body("login", equalTo(username)).extract().jsonPath();


        String login = responseAsJsonPath.getString("login");
        System.out.println(login);

        Map<String, Object> entireResponse = responseAsJsonPath.getMap("$");// $ represents the root element

        System.out.println(entireResponse);

        System.out.println(entireResponse.keySet());


    }

    @Test
    public void basicBodyValidationPath() {


        Response response = given().
                header("Accept", "application/vnd.github+json").
                header("X-GitHub-Api-Version", "2022-11-28").
                header("Authorization", "Bearer " + ConfigReader.getProperty("api.token")).

                when().log().all().
                get("/user").

                then().log().all().
                assertThat().
                statusCode((200)).
                body("plan.name", equalTo("free")).
                body("login", equalTo("dtacademyb11")).
                body("login", instanceOf(String.class)).
                body("id", equalTo(134797000)).
                body("id", instanceOf(Integer.class)).
                body("site_admin", instanceOf(Boolean.class)).
                body("twitter_username", nullValue()).
                body("login", notNullValue()).
                body("plan", hasKey("name")).
                body("plan", hasKey("name")).
                body("$", hasKey("id")).
                body("$", hasValue(134797000)).
                body("$", hasEntry("type" , "User")).
                body("$", not(equalTo(Collections.EMPTY_MAP))).
                body("plan", allOf(hasKey("name"),hasKey("space"),hasKey("collaborators"),hasKey("private_repos"))).
                time(lessThan(1000L)).
                extract().response();


        Map<String, Object> plan = response.path("plan");

        System.out.println(plan);
        System.out.println(plan.keySet());

        String name = response.path("plan.name");

        Assert.assertEquals("free", name);




    }

    @Test
    public void basicBodyValidationResponseAsList() {


        Response response = given().
                header("Accept", "application/vnd.github+json").


                when().log().all().
                get("/users").

                then().log().all().
                assertThat().
                statusCode((200)).
                body("$", hasSize(30)). // checks if the collection has certain size
                extract().response();


        List<Map<String,Object>> list = response.path("$");  // returns the root element which is a list, $ or empty string ->  root element

        System.out.println(list);

        Map<String, Object> firstElement = response.path("[0]");  // returns the first element(json) of the list

        System.out.println(firstElement);

        String login = response.path("[0].login");  // returns the login key's value of first element(json)

        System.out.println(login);

        List<String> logins = response.path("login");  // returns the login values of all json objects

        System.out.println(logins);






    }

    @Test
    public void basicBodyValidationComplexJson() {


        Response response = given().
                header("Accept", "application/vnd.github+json").
                 pathParam("username", "dtacademyb11").

                when().log().all().
                get("users/{username}/events").

                then().log().all().
                assertThat().
                statusCode((200)).
                body("[0].actor.login", equalTo("dtacademyb11")).
//                body("payload.push_id", not(hasItem(nullValue()))).
                body("type", hasItem("PushEvent")).
                body("type.", hasItems("PushEvent","PullRequestEvent")).
                body("[3].payload.pull_request.assignees", empty()).
                body("actor.login", everyItem(startsWith("dt"))).
                body("actor.login", everyItem(containsString("academy"))).
//                body("type", contains("PushEvent","PullRequestEvent")).

                extract().response();

          String loginNested = response.path("[0].actor.login");
        System.out.println(loginNested);


        System.out.println( (List)response.path("actor.login"));

        System.out.println( (String)response.path("[0].payload.commits[0].author.name"));

        List<Long> ids = response.path("payload.push_id");
        Assert.assertTrue(ids.contains(null));

        System.out.println(ids);



    }


}
