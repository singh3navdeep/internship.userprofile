package in.dailyhunt.internship.userprofile.repositories;

import in.dailyhunt.internship.userprofile.entities.LanguageData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LanguageDataRepository  extends JpaRepository<LanguageData, Long> {
    Optional<LanguageData> findByInjestionId(Long id);
}
