package davidegiliberti.capstone_backend.repositories;

import davidegiliberti.capstone_backend.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, UUID> {
    boolean existsByEmail(String email);

    Optional<Utente> findByEmail(String email);

    @Query(value = """
            SELECT u.*
            FROM utenti u
            JOIN utenti_videogioco uv ON u.id = uv.utente_id
            WHERE uv.videogioco_id =:videogioco_id
            """, nativeQuery = true)
    List<Utente> findAllUtenti(@Param("videogioco_id") UUID videogiocoId);
}
