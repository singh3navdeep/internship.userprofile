package in.dailyhunt.internship.userprofile.repositories;

import in.dailyhunt.internship.userprofile.entities.SourceData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface SourceDataRepository extends JpaRepository<SourceData, Long> {
    Optional<SourceData> findByInjestionId(Long id);
    List<SourceData> findAllByInjestionIdIn(Set<Long> ids);
}
