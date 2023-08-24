package pojos;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // getters, setters, toString, equals, hashcode
@AllArgsConstructor  //adds all args
@NoArgsConstructor // no args
@Builder  // lets you create objects using Builder pattern easily without using setters
@JsonInclude(JsonInclude.Include.NON_NULL) // to generate a dynamic json for initialized fields only, omits null fields
public class User {

    private String username;
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String createdAt;
    private String signUpDate;
    private String profilePic;

}
