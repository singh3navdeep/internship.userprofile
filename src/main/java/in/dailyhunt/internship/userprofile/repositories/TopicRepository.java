package in.dailyhunt.internship.userprofile.repositories;

import in.dailyhunt.internship.userprofile.entities.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {
    Optional<Topic> findByName(String name);
    Boolean existsByName(String name);
}
