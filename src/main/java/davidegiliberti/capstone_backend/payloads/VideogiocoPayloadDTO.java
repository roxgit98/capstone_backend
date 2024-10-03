package davidegiliberti.capstone_backend.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record VideogiocoPayloadDTO(
        @NotEmpty(message = "Titolo obbligatorio")
        String titolo,
        String descrizione,
        @NotEmpty(message = "Anno di pubblicazione obbligatorio")
        @Size(max = 4, message = "L'anno deve contenere 4 caratteri")
        int annoDiPubblicazione,
        String boxArt,
        @NotEmpty(message = "Piattaforma obbligatoria")
        String piattaforma,
        @NotEmpty(message = "Genere obbligatorio")
        String genere,
        int totaleOreDiGioco
) {
}
