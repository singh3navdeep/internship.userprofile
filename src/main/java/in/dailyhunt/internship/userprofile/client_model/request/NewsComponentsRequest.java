package in.dailyhunt.internship.userprofile.client_model.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class NewsComponentsRequest {

    @NotNull
    private Long id;

    @NotBlank
    private String name;
}
