package davidegiliberti.capstone_backend.controllers;

import davidegiliberti.capstone_backend.entities.Videogioco;
import davidegiliberti.capstone_backend.exceptions.BadRequestException;
import davidegiliberti.capstone_backend.exceptions.NotFoundException;
import davidegiliberti.capstone_backend.payloads.VideogiocoPayloadDTO;
import davidegiliberti.capstone_backend.services.VideogiocoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/videogiochi")
public class VideogiocoController {
    @Autowired
    private VideogiocoService videogiocoService;

    @GetMapping
    public Page<Videogioco> findAll(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "15") int size,
                                    @RequestParam(defaultValue = "id") String sortBy) {
        return this.videogiocoService.findAll(page, size, sortBy);
    }

    @GetMapping("/{videogiocoId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Videogioco findById(@PathVariable UUID videogiocoId) {
        Videogioco found = this.videogiocoService.findById(videogiocoId);
        if (found == null) throw new NotFoundException(videogiocoId);
        return found;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public Videogioco saveVideogioco(@RequestBody @Validated VideogiocoPayloadDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            String msg = validation.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).collect(Collectors.joining(". "));
            throw new BadRequestException("Errore nel payload: " + msg);
        } else {
            return this.videogiocoService.saveVideogioco(body);
        }
    }

    @PutMapping("/{videogiocoId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Videogioco findAndUpdate(@PathVariable UUID videogiocoId, @RequestBody @Validated VideogiocoPayloadDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            String msg = validation.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).collect(Collectors.joining(". "));
            throw new BadRequestException("Errore nel payload: " + msg);
        } else {
            return this.videogiocoService.findAndUpdate(videogiocoId, body);
        }

    }

    @DeleteMapping("/{videogiocoId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void findAndDelete(@PathVariable UUID videogiocoId) {
        this.videogiocoService.findAndDelete(videogiocoId);
    }

    @PatchMapping("/{videogiocoId}/boxArt")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Videogioco uploadBoxArt(@PathVariable UUID videogiocoId, @RequestParam("file") MultipartFile file) throws IOException {
        return this.videogiocoService.uploadBoxArt(videogiocoId, file);
    }

    @GetMapping("/orderAZ")
    public List<Videogioco> orderByTitolo() {
        return this.videogiocoService.orderByTitolo();
    }

}
