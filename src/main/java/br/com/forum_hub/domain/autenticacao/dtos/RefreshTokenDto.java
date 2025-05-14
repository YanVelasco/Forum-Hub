package br.com.forum_hub.domain.autenticacao.dtos;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenDto(
        @NotBlank String refreshToken
) {
}
