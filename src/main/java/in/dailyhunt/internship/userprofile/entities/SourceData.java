package in.dailyhunt.internship.userprofile.entities;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "source")
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class SourceData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long injestionId;

    @NotBlank
    @Size(min = 2, max = 20)
    private String name;

    private String imagepath;
}
