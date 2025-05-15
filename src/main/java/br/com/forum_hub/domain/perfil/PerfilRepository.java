package br.com.forum_hub.domain.perfil;

import br.com.forum_hub.domain.perfil.enums.PerfilNome;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerfilRepository extends JpaRepository<Perfil, Long> {
    Perfil findByPerfilNome(PerfilNome perfilNome);
}
