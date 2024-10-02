package davidegiliberti.capstone_backend.services;

import davidegiliberti.capstone_backend.entities.Utente;
import davidegiliberti.capstone_backend.exceptions.UnauthorizedException;
import davidegiliberti.capstone_backend.payloads.UtenteLoginDTO;
import davidegiliberti.capstone_backend.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationsService {
    @Autowired
    private UtenteService utenteService;
    @Autowired
    private JWTTools jwtTools;
    @Autowired
    private PasswordEncoder bcrypt;

    public String checkAndCreateToken(UtenteLoginDTO body) {
        Utente found = this.utenteService.findByEmail(body.email());
        if (bcrypt.matches(body.password(), found.getPassword())) {
            return this.jwtTools.createToken(found);
        } else {
            throw new UnauthorizedException("Errore nelle credenziali fornite");
        }
    }
}
