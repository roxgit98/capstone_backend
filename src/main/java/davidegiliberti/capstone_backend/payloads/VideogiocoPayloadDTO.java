package davidegiliberti.capstone_backend.payloads;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record VideogiocoPayloadDTO(
        @NotEmpty(message = "Titolo obbligatorio")
        String titolo,
        String descrizione,
        @NotNull(message = "Anno di pubblicazione obbligatorio")
        @Min(value = 1970, message = "L'anno deve essere almeno 1970")
        @Max(value = 2100, message = "L'anno deve essere massimo 2100")
        int annoDiPubblicazione,
        String boxArt,
        @NotEmpty(message = "Piattaforma obbligatoria")
        String piattaforma,
        @NotEmpty(message = "Genere obbligatorio")
        String genere,
        int totaleOreDiGioco
) {
}
