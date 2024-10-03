package davidegiliberti.capstone_backend.repositories;

import davidegiliberti.capstone_backend.entities.Videogioco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface VideogiocoRepository extends JpaRepository<Videogioco, UUID> {
    boolean existsByTitolo(String titolo);

    Optional<Videogioco> findByTitolo(String titolo);
}
