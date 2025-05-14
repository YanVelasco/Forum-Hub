package br.com.forum_hub.controller;

import br.com.forum_hub.domain.autenticacao.TokenService;
import br.com.forum_hub.domain.autenticacao.dtos.DadosLogin;
import br.com.forum_hub.domain.autenticacao.dtos.LoginResponseDto;
import br.com.forum_hub.domain.autenticacao.dtos.RefreshTokenDto;
import br.com.forum_hub.domain.usuario.Usuario;
import br.com.forum_hub.domain.usuario.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AtenticacaoController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UsuarioRepository usuarioRepository;

    public AtenticacaoController(AuthenticationManager authenticationManager, TokenService tokenService, UsuarioRepository usuarioRepository) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> efetuarLogin(@RequestBody @Valid DadosLogin dados){
        var autheticationToken = new UsernamePasswordAuthenticationToken(dados.email(), dados.senha());
        var authentication = authenticationManager.authenticate(autheticationToken);
        var token = tokenService.gerarToken((Usuario) authentication.getPrincipal());
        String refreshToken = tokenService.gerarRefreshToken((Usuario) authentication.getPrincipal());
        return ResponseEntity.ok(new LoginResponseDto(token, refreshToken));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<LoginResponseDto> refreshToken(@RequestBody @Valid RefreshTokenDto refreshTokenDto){
        var token = refreshTokenDto.refreshToken();
        Long id = Long.valueOf(tokenService.verificarToken(token));
        var usuario = usuarioRepository.findById(id).orElseThrow();
        String newToken = tokenService.gerarToken(usuario);
        String newRefreshToken = tokenService.gerarRefreshToken(usuario);
        return ResponseEntity.ok(new LoginResponseDto(newToken, newRefreshToken));
    }

}
