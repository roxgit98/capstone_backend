package davidegiliberti.capstone_backend.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import davidegiliberti.capstone_backend.entities.Videogioco;
import davidegiliberti.capstone_backend.exceptions.BadRequestException;
import davidegiliberti.capstone_backend.exceptions.NotFoundException;
import davidegiliberti.capstone_backend.payloads.VideogiocoPayloadDTO;
import davidegiliberti.capstone_backend.repositories.VideogiocoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class VideogiocoService {
    @Autowired
    private VideogiocoRepository videogiocoRepository;
    @Autowired
    private Cloudinary cloudinary;

    public Page<Videogioco> findAll(int page, int size, String sortBy) {
        if (page > 100) page = 100;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.videogiocoRepository.findAll(pageable);
    }

    public Videogioco findById(UUID videogiocoId) {
        Videogioco found = this.videogiocoRepository.findById(videogiocoId).orElseThrow(() -> new NotFoundException(videogiocoId));
        return found;
    }

    public Videogioco findAndUpdate(UUID videogiocoId, VideogiocoPayloadDTO body) {
        String boxArt = "https://ui-avatars.com/api/?name=" + body.titolo();
        Videogioco found = this.videogiocoRepository.findById(videogiocoId).orElseThrow(() -> new NotFoundException(videogiocoId));
        if (found == null) throw new NotFoundException(videogiocoId);
        found.setTitolo(body.titolo());
        found.setDescrizione(body.descrizione());
        found.setAnnoDiPubblicazione(body.annoDiPubblicazione());
        found.setPiattaforma(body.piattaforma());
        found.setGenere(body.genere());

        return videogiocoRepository.save(found);
    }

    public Videogioco saveVideogioco(VideogiocoPayloadDTO body) {
        if (videogiocoRepository.existsByTitolo(body.titolo()))
            throw new BadRequestException("Questo titolo è già presente nel catalogo");
        String boxArt = "https://ui-avatars.com/api/?name=" + body.titolo();
        Videogioco newVideogioco = new Videogioco(body.titolo(), body.descrizione(), body.annoDiPubblicazione(), boxArt, body.piattaforma(), body.genere(), body.totaleOreDiGioco());

        return videogiocoRepository.save(newVideogioco);
    }

    public void findAndDelete(UUID videogiocoId) {
        Videogioco found = this.videogiocoRepository.findById(videogiocoId).orElseThrow(() -> new NotFoundException(videogiocoId));
        if (found == null) throw new NotFoundException(videogiocoId);
        this.videogiocoRepository.delete(found);
    }

    public Videogioco findByTitolo(String titolo) {
        return this.videogiocoRepository.findByTitolo(titolo).orElseThrow(() -> new NotFoundException(titolo));
    }

    public Videogioco uploadBoxArt(UUID videogiocoId, MultipartFile pic) throws IOException {
        Videogioco found = this.findById(videogiocoId);

        String url = (String) cloudinary.uploader().upload(pic.getBytes(), ObjectUtils.emptyMap()).get("url");
        System.out.println("URL: " + url);

        found.setBoxArt(url);
        return this.videogiocoRepository.save(found);
    }

    public List<Videogioco> orderByTitolo() {
        return videogiocoRepository.orderByTitolo();
    }
}
