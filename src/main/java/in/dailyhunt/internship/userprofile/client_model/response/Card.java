package in.dailyhunt.internship.userprofile.client_model.response;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Data
@Builder
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long injestionId;

    @Column(columnDefinition = "TEXT")
    private String title;

    @ElementCollection
    private Set<Long> tagIds;

    @ElementCollection
    private Set<Long> localityIds;

    @ElementCollection
    private Set<Long> genreIds;

    @ElementCollection
    private Set<Long> languageIds;

    @Column
    private String thumbnailPath;

    @Column
    private Boolean trending;

    @Column
    private Date publishedAt;
}
