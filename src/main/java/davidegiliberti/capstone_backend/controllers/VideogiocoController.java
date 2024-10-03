package davidegiliberti.capstone_backend.controllers;

import davidegiliberti.capstone_backend.entities.Videogioco;
import davidegiliberti.capstone_backend.exceptions.NotFoundException;
import davidegiliberti.capstone_backend.services.VideogiocoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/videogiochi")
public class VideogiocoController {
    @Autowired
    private VideogiocoService videogiocoService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
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


}
