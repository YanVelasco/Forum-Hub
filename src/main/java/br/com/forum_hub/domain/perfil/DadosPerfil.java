package br.com.forum_hub.domain.perfil;

import br.com.forum_hub.domain.perfil.enums.PerfilNome;
import jakarta.validation.constraints.NotNull;

public record DadosPerfil(
        @NotNull PerfilNome perfilNome
) {
}
