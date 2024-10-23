package davidegiliberti.capstone_backend.payloads;

import davidegiliberti.capstone_backend.enums.RuoloUtente;

import java.util.UUID;

public record UtenteLoginRespDTO(String accessToken, RuoloUtente ruolo, UUID utenteId, String username, String avatar) {
}
