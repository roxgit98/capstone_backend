package davidegiliberti.capstone_backend.controllers;

import davidegiliberti.capstone_backend.entities.Utente;
import davidegiliberti.capstone_backend.enums.RuoloUtente;
import davidegiliberti.capstone_backend.exceptions.BadRequestException;
import davidegiliberti.capstone_backend.payloads.UtenteLoginDTO;
import davidegiliberti.capstone_backend.payloads.UtenteLoginRespDTO;
import davidegiliberti.capstone_backend.payloads.UtentePayloadDTO;
import davidegiliberti.capstone_backend.payloads.UtenteRespDTO;
import davidegiliberti.capstone_backend.services.AuthorizationsService;
import davidegiliberti.capstone_backend.services.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthorizationController {
    @Autowired
    private AuthorizationsService authorizationsService;
    @Autowired
    private UtenteService utenteService;

    @PostMapping("/login")
    public UtenteLoginRespDTO login(@RequestBody UtenteLoginDTO body) {
        Utente found = this.utenteService.findByEmail(body.email());
        RuoloUtente ruolo = found.getRuolo();
        UUID utenteId = found.getId();
        return new UtenteLoginRespDTO(this.authorizationsService.checkAndCreateToken(body), ruolo, utenteId);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UtenteRespDTO save(@RequestBody @Validated UtentePayloadDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            String msg = validation.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).collect(Collectors.joining(". "));
            throw new BadRequestException("Errore nel payload " + msg);
        } else {
            return new UtenteRespDTO(this.utenteService.saveUtente(body).getId());
        }
    }
}
