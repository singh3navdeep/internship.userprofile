package in.dailyhunt.internship.userprofile.client_model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Builder
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

    private Long news_language;

    @Size(min = 6, max = 40, message = "Password must be atleast 6 to 40 characters long")
    private String password;

    private Optional<String> base64;


    private Optional<Set<Long>> following_genres;

    private Optional<Set<Long>> following_languages;

    private Optional<Set<Long>> following_localities;

    private Optional<Set<Long>> following_tags;

    private Optional<Set<Long>> blocked_genres;

    private Optional<Set<Long>> blocked_languages;

    private Optional<Set<Long>> blocked_localities;

    private Optional<Set<Long>> blocked_tags;



//    private String role;

}
