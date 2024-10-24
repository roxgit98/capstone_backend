package davidegiliberti.capstone_backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import davidegiliberti.capstone_backend.enums.RuoloUtente;
import davidegiliberti.capstone_backend.exceptions.BadRequestException;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "utenti")
@JsonIgnoreProperties({"password", "authorities", "enabled", "accountNonLocked", "accountNonExpired", "credentialsNonExpired"})
public class Utente implements UserDetails {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    private String username;
    private String email;
    private String password;
    private String nome;
    private String cognome;
    private String avatar;
    @Enumerated(EnumType.STRING)
    private RuoloUtente ruolo;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Videogioco> videogioco;

    public Utente(String username, String email, String password, String nome, String cognome, String avatar, List<Videogioco> videogioco) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.nome = nome;
        this.cognome = cognome;
        this.avatar = avatar;
        this.ruolo = RuoloUtente.USER;
        this.videogioco = videogioco;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.ruolo.name()));
    }

    public void addVideogioco(Videogioco videogioco) {
        if (!this.videogioco.contains(videogioco)) {
            this.videogioco.add(videogioco);
        } else {
            throw new BadRequestException("Il Titolo è già presente nella collezione");
        }

    }

    public void removeVideogioco(Videogioco videogioco) {
        if (this.videogioco.contains(videogioco)) {
            this.videogioco.remove(videogioco);
        } else {
            throw new BadRequestException("Il Titolo non è presente nella collezione");
        }
    }
}
