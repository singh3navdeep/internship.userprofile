package in.dailyhunt.internship.userprofile.repositories;

import in.dailyhunt.internship.userprofile.entities.TagData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface TagDataRepository extends JpaRepository<TagData, Long> {
    Optional<TagData> findByInjestionId(Long id);
    Set<TagData> findAllByInjestionId(Set<Long> ids);
}
