package in.dailyhunt.internship.userprofile.repositories;

import in.dailyhunt.internship.userprofile.entities.GenreData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreDataRepository extends JpaRepository<GenreData, Long> {
    GenreData findByInjestionId(Long id);
}
