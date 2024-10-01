package davidegiliberti.capstone_backend.services;

import davidegiliberti.capstone_backend.entities.Utente;
import davidegiliberti.capstone_backend.exceptions.NotFoundException;
import davidegiliberti.capstone_backend.repositories.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UtenteService {
    @Autowired
    private UtenteRepository utenteRepository;
    @Autowired
    private PasswordEncoder bcrypt;


    public Utente findById(UUID utenteId) {
        Utente found = this.utenteRepository.findById(utenteId).orElseThrow(() -> new NotFoundException(utenteId));
        return found;
    }
}
