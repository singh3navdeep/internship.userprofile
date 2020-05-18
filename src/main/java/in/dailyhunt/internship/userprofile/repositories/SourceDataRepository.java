package in.dailyhunt.internship.userprofile.repositories;

import in.dailyhunt.internship.userprofile.entities.SourceData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SourceDataRepository extends JpaRepository<SourceData, Long> {
    Optional<SourceData> findByInjestionId(Long id);
}
