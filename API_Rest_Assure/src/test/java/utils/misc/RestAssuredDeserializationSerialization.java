package utils.misc;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import org.junit.Assert;
import org.junit.Test;
import pojos.PostUserResponse;
import pojos.User;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class RestAssuredDeserializationSerialization {


    @Test
    public void serializeAsString() {
        RestAssured.baseURI = "http://duotify.us-east-2.elasticbeanstalk.com/api";

        String username = new Faker().name().username();
        String email = new Faker().internet().emailAddress();

        given().
                header("Content-Type", "application/json").
                queryParam("api_key", "e82042a5f58f449c9d5a9e3cf5a3f43b").
//                body("""
//                          {
//                            "username": "coolherc205599889",
//                            "firstName": "Cool",
//                            "lastName": "Herc",
//                            "email": "coolherc205599889@mail.com",
//                            "password": "coolherc"
//                          }
//      """ ).

        body("{\n" +
        "  \"username\": \"" + username + "\",\n" +
        "  \"firstName\": \"Cool\",\n" +
        "  \"lastName\": \"Herc\",\n" +
        "  \"email\": \"" + email + "\",\n" +
        "  \"password\": \"dhsjjfhdsf\"\n" +
        "}").

                when().log().all().
                post("/user").
                then().log().all().
                statusCode(201);

    }

    @Test
    public void serializeAsFile() {
        RestAssured.baseURI = "http://duotify.us-east-2.elasticbeanstalk.com/api";


        Integer userId = given().
                header("Content-Type", "application/json").
                queryParam("api_key", "e82042a5f58f449c9d5a9e3cf5a3f43b").
                body(new File("src/test/java/utils/misc/userPayload.json")).

                when().log().all().
                post("/user").
                then().log().all().
                statusCode(201).extract().path("user_id");

        given().

                queryParam("api_key", "e82042a5f58f449c9d5a9e3cf5a3f43b").
                queryParam("id", userId).
//
        when().log().all().
                delete("/user").
                then().log().all().
                statusCode(200);

    }

    @Test
    public void serializeAsMap() {
        RestAssured.baseURI = "http://duotify.us-east-2.elasticbeanstalk.com/api";

        Map<String, Object> userAsMap = new LinkedHashMap<>();

        userAsMap.put("username", new Faker().name().username());
        userAsMap.put("lastName", new Faker().name().lastName());
        userAsMap.put("firstName", new Faker().name().firstName());
        userAsMap.put("email", new Faker().internet().emailAddress());
        userAsMap.put("password", new Faker().internet().password());


        given().
                header("Content-Type", "application/json").
                queryParam("api_key", "e82042a5f58f449c9d5a9e3cf5a3f43b").
                body(userAsMap).
                when().log().all().
                post("/user").
                then().log().all().
                statusCode(201);


    }

    @Test
    public void serializeAsList() {
        RestAssured.baseURI = "http://duotify.us-east-2.elasticbeanstalk.com/api";

        List<String> payloadAsList = List.of("email1@gmail.com", "email2@gmail.com", "email3@gmail.com", "email4@gmail.com");


        given().
                header("Content-Type", "application/json").
                queryParam("api_key", "e82042a5f58f449c9d5a9e3cf5a3f43b").
                // you can explicitly choose the serializer/deserializer library with ObjectMapperType in body() method
//                body(payloadAsList, ObjectMapperType.JACKSON_2). // for json
//                body(payloadAsList, ObjectMapperType.JAXB). // for xml
        body(payloadAsList). // if no explicit ObjectMapper is added, the default or first available one will be used by Rest Assured
                when().log().all().
                post("/user").
                then().log().all().
                statusCode(422);


    }

    @Test
    public void serializeAsPOJO() {
        RestAssured.baseURI = "http://duotify.us-east-2.elasticbeanstalk.com/api";

        User userAsPayload = User.builder().username(new Faker().name().username()).
                firstName(new Faker().name().firstName()).
                lastName(new Faker().name().lastName()).
                email(new Faker().internet().emailAddress()).
                password(new Faker().internet().password()).build();


        System.out.println(userAsPayload);


        given().
                header("Content-Type", "application/json").
                queryParam("api_key", "e82042a5f58f449c9d5a9e3cf5a3f43b").

                body(userAsPayload).
                when().log().all().
                post("/user").
                then().log().all().
                statusCode(201);


    }

    @Test
    public void serializeAsPOJODynamic() {
        RestAssured.baseURI = "http://duotify.us-east-2.elasticbeanstalk.com/api";

//        User user =  new User();
////        user.setUsername("exampleUsername");
////        user.setEmail("exampleUsername@gmail.com");
//        user.setFirstName("First");
//        user.setLastName("Last");

        User user = User.builder()
                .email("Asav@gmail.com")
                .lastName("dsd").build();


        System.out.println(user);


        given().
                header("Content-Type", "application/json").
                queryParam("api_key", "e82042a5f58f449c9d5a9e3cf5a3f43b").

                body(user).
                when().log().all().
                post("/user").
                then().log().all().
                statusCode(422);


    }

    @Test
    public void deSerializeAsString() {


        RestAssured.baseURI = "http://duotify.us-east-2.elasticbeanstalk.com/api";

        String username = new Faker().name().username();
        String email = new Faker().internet().emailAddress();

        String responseAsString = given().
                header("Content-Type", "application/json").
                queryParam("api_key", "e82042a5f58f449c9d5a9e3cf5a3f43b").

                body("{\n" +
                        "  \"username\": \"" + username + "\",\n" +
                        "  \"firstName\": \"Cool\",\n" +
                        "  \"lastName\": \"Herc\",\n" +
                        "  \"email\": \"" + email + "\",\n" +
                        "  \"password\": \"dhsjjfhdsf\"\n" +
                        "}").

                when().log().all().
                post("/user").
                then().log().all().
//                statusCode(201).extract().asString();
        statusCode(201).extract().asPrettyString();

        System.out.println(responseAsString.contains("The user has been created."));

    }

    @Test
    public void deSerializeAsRawMap() {


        RestAssured.baseURI = "http://duotify.us-east-2.elasticbeanstalk.com/api";

        String username = new Faker().name().username();
        String email = new Faker().internet().emailAddress();

        Map responseAsRawMap = given().
                header("Content-Type", "application/json").
                queryParam("api_key", "e82042a5f58f449c9d5a9e3cf5a3f43b").

                body("{\n" +
                        "  \"username\": \"" + username + "\",\n" +
                        "  \"firstName\": \"Cool\",\n" +
                        "  \"lastName\": \"Herc\",\n" +
                        "  \"email\": \"" + email + "\",\n" +
                        "  \"password\": \"dhsjjfhdsf\"\n" +
                        "}").

                when().log().all().
                post("/user").
                then().log().all().
                statusCode(201).extract().as(Map.class);

        System.out.println(responseAsRawMap);
        Object userId = responseAsRawMap.get("user_id");
        System.out.println(userId);


    }

    @Test
    public void deSerializeAsSpecificMap() {


        RestAssured.baseURI = "http://duotify.us-east-2.elasticbeanstalk.com/api";

        String username = new Faker().name().username();
        String email = new Faker().internet().emailAddress();

        Map<String, Object> responseAsSpecificMap = given().
                header("Content-Type", "application/json").
                queryParam("api_key", "e82042a5f58f449c9d5a9e3cf5a3f43b").

                body("{\n" +
                        "  \"username\": \"" + username + "\",\n" +
                        "  \"firstName\": \"Cool\",\n" +
                        "  \"lastName\": \"Herc\",\n" +
                        "  \"email\": \"" + email + "\",\n" +
                        "  \"password\": \"dhsjjfhdsf\"\n" +
                        "}").

                when().log().all().
                post("/user").
                then().log().all().
                statusCode(201).extract().as(new TypeRef<Map<String, Object>>() {
                });
//
        System.out.println(responseAsSpecificMap);
        Object userId = responseAsSpecificMap.get("user_id");
        System.out.println(userId);


    }


    @Test
    public void deSerializeAsList() {


        RestAssured.baseURI = "http://duotify.us-east-2.elasticbeanstalk.com/api";


        List<Map<String, Object>> list = given().
                header("Content-Type", "application/json").
                queryParam("api_key", "e82042a5f58f449c9d5a9e3cf5a3f43b").


                when().log().all().
                get("/users").
                then().log().all().
//                statusCode(200).extract().as(List.class) raw type
        statusCode(200).extract().as(new TypeRef<List<Map<String, Object>>>() {
                });


        System.out.println(list);
        System.out.println(list.get(1).get("username"));


    }


    @Test
    public void deSerializeAsPOJO() {


        RestAssured.baseURI = "http://duotify.us-east-2.elasticbeanstalk.com/api";

        String username = new Faker().name().username();
        String email = new Faker().internet().emailAddress();

        PostUserResponse userResponseAsPojo = given().
                header("Content-Type", "application/json").
                queryParam("api_key", "e82042a5f58f449c9d5a9e3cf5a3f43b").

                body("{\n" +
                        "  \"username\": \"" + username + "\",\n" +
                        "  \"firstName\": \"Cool\",\n" +
                        "  \"lastName\": \"Herc\",\n" +
                        "  \"email\": \"" + email + "\",\n" +
                        "  \"password\": \"dhsjjfhdsf\"\n" +
                        "}").

                when().log().all().
                post("/user").
                then().log().all().
                statusCode(201).extract().as(PostUserResponse.class);

        System.out.println(userResponseAsPojo);

        Assert.assertEquals(Integer.valueOf(1), userResponseAsPojo.getStatus());

//


    }

    @Test
    public void deSerializeAsPOJO2() {


        RestAssured.baseURI = "http://duotify.us-east-2.elasticbeanstalk.com/api";


        User user = given().
                header("Content-Type", "application/json").
                queryParam("api_key", "e82042a5f58f449c9d5a9e3cf5a3f43b").
                queryParam("id", 67).

                when().log().all().
                get("/user").
                then().log().all().
                statusCode(200).extract().as(User.class);

        System.out.println(user);

        System.out.println(user.getEmail());
        System.out.println(user.getCreatedAt());
        System.out.println(user.getUsername());


//


    }

    @Test
    public void deSerializeAsPojo3() {


        RestAssured.baseURI = "http://duotify.us-east-2.elasticbeanstalk.com/api";


        List<User> list = given().
                header("Content-Type", "application/json").
                queryParam("api_key", "e82042a5f58f449c9d5a9e3cf5a3f43b").


                when().log().all().
                get("/users").
                then().log().all().
//
        statusCode(200).extract().as(new TypeRef<List<User>>() {
                });

        List<String> ids = new ArrayList<>();
        for (User user : list) {
            ids.add(user.getId());
        }
        System.out.println(ids);

//        System.out.println(list.get(2).getEmail());

    }

}
