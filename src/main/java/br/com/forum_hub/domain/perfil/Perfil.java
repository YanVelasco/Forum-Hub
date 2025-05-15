package br.com.forum_hub.domain.perfil;

import br.com.forum_hub.domain.perfil.enums.PerfilNome;
import jakarta.persistence.*;

@Entity
@Table(name="perfis")
public class Perfil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(unique = true)
    private PerfilNome perfilNome;

}
