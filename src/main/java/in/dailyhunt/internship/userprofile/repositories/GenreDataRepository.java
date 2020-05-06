package in.dailyhunt.internship.userprofile.repositories;

import in.dailyhunt.internship.userprofile.entities.GenreData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface GenreDataRepository extends JpaRepository<GenreData, Long> {
    Optional<GenreData> findByInjestionId(Long id);
    List<GenreData> findAllByInjestionIdIn(Set<Long> ids);
    Set<GenreData> findByGenericTrue();
}
