package in.dailyhunt.internship.userprofile.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import in.dailyhunt.internship.userprofile.enums.GenderValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "USERS")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;


 //   private String image_path;

    @Size(min = 3, max = 50)
    @Column(name = "NAME")
    private String name;

    @Size(min = 3, max = 50)
    @Column(name = "USER_NAME")
    private String username;

    @NaturalId
    @Size(max = 50)
    @Email
    @Column(unique = true, name = "EMAIL")
    private String email;

    @Size(min=6, max = 100)
    @Column(name = "PASSWORD")
    private String password;

    @Enumerated(EnumType.STRING)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinTable(name = "USER_GENDER_MAPPING",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "GENDER_ID"))
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Gender gender;

    @Column(name = "DATE_OF_BIRTH")
    private Date date_of_birth;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinTable(name = "USER_NEWS_LANGUAGE_MAPPING",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "LANGUAGE_ID"))
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Language news_language;


}
