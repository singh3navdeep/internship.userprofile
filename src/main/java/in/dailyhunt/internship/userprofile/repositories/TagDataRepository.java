package in.dailyhunt.internship.userprofile.repositories;

import in.dailyhunt.internship.userprofile.entities.TagData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagDataRepository extends JpaRepository<TagData, Long> {
    TagData findByInjestionId(Long id);
}
