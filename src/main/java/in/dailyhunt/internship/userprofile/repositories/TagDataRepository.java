package in.dailyhunt.internship.userprofile.repositories;

import in.dailyhunt.internship.userprofile.entities.TagData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagDataRepository extends JpaRepository<TagData, Long> {
    Optional<TagData> findByInjestionId(Long id);
}
