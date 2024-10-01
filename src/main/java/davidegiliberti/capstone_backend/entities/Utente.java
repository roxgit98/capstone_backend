package davidegiliberti.capstone_backend.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "utenti")
public class Utente {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    private String username;
    private String nome;
    private String cognome;
    private String avatar;
    @ManyToMany
    private List<Videogioco> videogioco;

    public Utente(String username, String nome, String cognome, String avatar, List<Videogioco> videogioco) {
        this.username = username;
        this.nome = nome;
        this.cognome = cognome;
        this.avatar = avatar;
        this.videogioco = videogioco;
    }
}
