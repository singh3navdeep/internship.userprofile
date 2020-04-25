package in.dailyhunt.internship.userprofile.repositories;

import in.dailyhunt.internship.userprofile.entities.LocalityData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocalityDataRepository  extends JpaRepository<LocalityData, Long> {
    LocalityData findByInjestionId(Long id);
}
