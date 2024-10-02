package davidegiliberti.capstone_backend.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import davidegiliberti.capstone_backend.entities.Utente;
import davidegiliberti.capstone_backend.exceptions.BadRequestException;
import davidegiliberti.capstone_backend.exceptions.NotFoundException;
import davidegiliberti.capstone_backend.payloads.UtentePayloadDTO;
import davidegiliberti.capstone_backend.repositories.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class UtenteService {
    @Autowired
    private UtenteRepository utenteRepository;
    @Autowired
    private PasswordEncoder bcrypt;
    @Autowired
    private Cloudinary cloudinary;

    public Page<Utente> findAll(int page, int size, String sortBy) {
        if (page > 100) page = 100;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.utenteRepository.findAll(pageable);
    }


    public Utente findById(UUID utenteId) {
        Utente found = this.utenteRepository.findById(utenteId).orElseThrow(() -> new NotFoundException(utenteId));
        return found;
    }

    public Utente findAndUpdate(UUID utenteId, UtentePayloadDTO body) {
        String avatar = "https://ui-avatars.com/api/?name=" + body.nome() + "+" + body.cognome();
        Utente found = this.utenteRepository.findById(utenteId).orElseThrow(() -> new NotFoundException(utenteId));
        if (found == null) throw new NotFoundException(utenteId);
        found.setUsername(body.username());
        found.setEmail(body.email());
        found.setPassword(body.password());
        found.setNome(body.nome());
        found.setCognome(body.cognome());

        return utenteRepository.save(found);
    }

    public Utente saveUtente(UtentePayloadDTO body) {
        if (utenteRepository.existsByEmail(body.email()))
            throw new BadRequestException("Email già utilizzata");
        String avatar = "https://ui-avatars.com/api/?name=" + body.nome() + "+" + body.cognome();
        Utente newUtente = new Utente(body.username(), body.email(), bcrypt.encode(body.password()), body.nome(), body.cognome(), avatar);

        return utenteRepository.save(newUtente);
    }

    public void findAndDelete(UUID utenteId) {
        Utente found = this.utenteRepository.findById(utenteId).orElseThrow(() -> new NotFoundException(utenteId));
        if (found == null) throw new NotFoundException(utenteId);
        this.utenteRepository.delete(found);
    }

    public Utente findByEmail(String email) {
        return this.utenteRepository.findByEmail(email).orElseThrow(() -> new NotFoundException(email));
    }

    public Utente uploadAvatar(UUID utenteId, MultipartFile pic) throws IOException {
        Utente found = this.findById(utenteId);

        String url = (String) cloudinary.uploader().upload(pic.getBytes(), ObjectUtils.emptyMap()).get("url");
        System.out.println("URL: " + url);

        found.setAvatar(url);
        return this.utenteRepository.save(found);
    }
}
