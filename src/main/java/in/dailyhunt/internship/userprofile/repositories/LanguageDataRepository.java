package in.dailyhunt.internship.userprofile.repositories;

import in.dailyhunt.internship.userprofile.entities.LanguageData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LanguageDataRepository  extends JpaRepository<LanguageData, Long> {
    LanguageData findByInjestionId(Long id);
}
