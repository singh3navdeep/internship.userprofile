package in.dailyhunt.internship.userprofile.client_model.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SourceRequest {

    @NotNull
    private Long id;

    private String name;

    private String imagepath;
}
