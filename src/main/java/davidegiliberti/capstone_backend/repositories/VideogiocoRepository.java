package davidegiliberti.capstone_backend.repositories;

import davidegiliberti.capstone_backend.entities.Videogioco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VideogiocoRepository extends JpaRepository<Videogioco, UUID> {
    boolean existsByTitolo(String titolo);

    Optional<Videogioco> findByTitolo(String titolo);

    @Query("SELECT v FROM Videogioco v ORDER BY v.titolo ASC")
    List<Videogioco> orderByTitolo();
}
