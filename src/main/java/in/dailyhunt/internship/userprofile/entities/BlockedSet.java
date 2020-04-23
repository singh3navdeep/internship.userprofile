package in.dailyhunt.internship.userprofile.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Builder
@Table(name = "BLOCKED")
public class BlockedSet {

    @Id
    @NotNull
    private Long userId;

    @ElementCollection
    private List<Long> genreIds;

    @ElementCollection
    private List<Long> languageIds;

    @ElementCollection
    private List<Long> localityIds;

    @ElementCollection
    private List<Long> tagIds;
}
