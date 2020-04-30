package in.dailyhunt.internship.userprofile.repositories;

import in.dailyhunt.internship.userprofile.entities.LocalityData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LocalityDataRepository  extends JpaRepository<LocalityData, Long> {
    Optional<LocalityData> findByInjestionId(Long id);
}
