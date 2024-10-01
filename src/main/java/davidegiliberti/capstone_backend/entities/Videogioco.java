package davidegiliberti.capstone_backend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "videogiochi")
public class Videogioco {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    private String titolo;
    private String descrizione;
    private int annoDiPubblicazione;
    private String boxArt;
    private String piattaforma;
    private String genere;

    public Videogioco(String titolo, String descrizione, int annoDiPubblicazione, String boxArt, String piattaforma, String genere) {
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.annoDiPubblicazione = annoDiPubblicazione;
        this.boxArt = boxArt;
        this.piattaforma = piattaforma;
        this.genere = genere;
    }
}
