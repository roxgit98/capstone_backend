package davidegiliberti.capstone_backend.repositories;

import davidegiliberti.capstone_backend.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, UUID> {
    boolean existsByEmail(String email);

    Optional<Utente> findByEmail(String email);
}
