package in.dailyhunt.internship.userprofile.client_model.response;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class Genre {

    @NotNull
    private Long id;
    @NotBlank
    private String name;
    @NotNull
    private Long postCount;
    @NotNull
    private Boolean active;
}
