package in.dailyhunt.internship.userprofile.client_model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Article {
    private Long id;

    private String title;

    private String text;

    private String shortText;

    private String imagePath;

}
