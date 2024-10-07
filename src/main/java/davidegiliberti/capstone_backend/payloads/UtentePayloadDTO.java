package davidegiliberti.capstone_backend.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record UtentePayloadDTO(
        @NotEmpty(message = "Username obbligatorio")
        @Size(min = 3, max = 30, message = "Lo username deve contenere dai 3 ai 30 caratteri")
        String username,
        @NotEmpty(message = "Email obbligatoria")
        @Email(message = "Inserisci un'email valida")
        String email,
        @NotEmpty(message = "Password obbligatoria")
        @Size(min = 8, message = "La password deve contenere almeno 8 caratteri")
        String password,
        @NotEmpty(message = "Nome obbligatorio")
        @Size(max = 10, message = "Il nome deve contenere massimo 10 caratteri")
        String nome,
        @NotEmpty(message = "Cognome obbligatorio")
        @Size(max = 20, message = "Il Cognome deve contenere massimo 20 caratteri")
        String cognome,
        String avatar
) {
}
