package davidegiliberti.capstone_backend.controllers;

import davidegiliberti.capstone_backend.entities.Utente;
import davidegiliberti.capstone_backend.exceptions.BadRequestException;
import davidegiliberti.capstone_backend.exceptions.NotFoundException;
import davidegiliberti.capstone_backend.payloads.UtentePayloadDTO;
import davidegiliberti.capstone_backend.services.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/utenti")
public class UtenteController {
    @Autowired
    private UtenteService utenteService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.FOUND)
    public Page<Utente> findAll(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "15") int size,
                                @RequestParam(defaultValue = "id") String sortBy) {
        return this.utenteService.findAll(page, size, sortBy);
    }

    @GetMapping("/{utenteId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.FOUND)
    public Utente findById(@PathVariable UUID utenteId) {
        Utente found = this.utenteService.findById(utenteId);
        if (found == null) throw new NotFoundException(utenteId);
        return found;
    }

    @PutMapping("/{utenteId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Utente findAndUpdate(@PathVariable UUID utenteId, @RequestBody @Validated UtentePayloadDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            String msg = validation.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).collect(Collectors.joining(". "));
            throw new BadRequestException("Errore nel payload: " + msg);
        } else {
            return this.utenteService.findAndUpdate(utenteId, body);
        }
    }

    @DeleteMapping("/{utenteId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.GONE)
    public void findAndDelete(@PathVariable UUID utenteId) {
        this.utenteService.findAndDelete(utenteId);
    }

    @GetMapping("/me")
    public Utente getProfile(@AuthenticationPrincipal Utente currentUser) {
        return currentUser;
    }

    @PutMapping("/me")
    public Utente updateProfile(@AuthenticationPrincipal Utente currentUser, @RequestBody UtentePayloadDTO body) {
        return this.utenteService.findAndUpdate(currentUser.getId(), body);
    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.GONE)
    public void deleteProfile(@AuthenticationPrincipal Utente currentUser) {
        this.utenteService.findAndDelete(currentUser.getId());
    }

    @PatchMapping("/me")
    public Utente uploadAvatar(@AuthenticationPrincipal Utente utente, @RequestParam("pic") MultipartFile pic) throws IOException {
        return this.utenteService.uploadAvatar(utente.getId(), pic);
    }
}
