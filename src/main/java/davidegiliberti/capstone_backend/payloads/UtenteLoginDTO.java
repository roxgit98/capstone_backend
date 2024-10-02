package davidegiliberti.capstone_backend.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record UtenteLoginDTO(
        @NotEmpty(message = "Email obbligatoria")
        @Email(message = "Inserisci un'email valida")
        String email,
        @NotEmpty(message = "inserisci una password")
        @Size(min = 8, max = 12, message = "La password deve contenere dai 8 ai 12 caratteri")
        String password
) {
}
