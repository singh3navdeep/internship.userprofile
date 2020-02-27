package in.dailyhunt.internship.userprofile.client_model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import in.dailyhunt.internship.userprofile.entities.Topic;
import in.dailyhunt.internship.userprofile.enums.GenderValue;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

@Data
public class SignUpForm {

    @Size(min = 3, max = 50, message = "Name must be atleast 3 to 50 characters long")
    private String name;

    @Size(max = 60)
    @Email(message = "Please enter valid email")
    private String email;

    @Size(max = 60)
    private String username;

    @Enumerated(EnumType.STRING)
    private String gender;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date date_of_birth;

    private String news_language;

    @Size(min = 6, max = 40, message = "Password must be atleast 6 to 40 characters long")
    private String password;


    private Set<String> following;

    private Set<String> blocked;
}
