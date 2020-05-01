package in.dailyhunt.internship.userprofile.repositories;

import in.dailyhunt.internship.userprofile.entities.LocalityData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface LocalityDataRepository  extends JpaRepository<LocalityData, Long> {
    Optional<LocalityData> findByInjestionId(Long id);
    List<LocalityData> findAllByInjestionIdIn(Set<Long> ids);
}
