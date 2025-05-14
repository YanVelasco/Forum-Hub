package br.com.forum_hub.controller;

import br.com.forum_hub.domain.autenticacao.dtos.DadosLogin;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AtenticacaoController {

    private final AuthenticationManager authenticationManager;

    public AtenticacaoController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<Authentication> efetuarLogin(@RequestBody @Valid DadosLogin dados){
        var autheticationToken = new UsernamePasswordAuthenticationToken(dados.email(), dados.senha());
        var authentication = authenticationManager.authenticate(autheticationToken);
        return ResponseEntity.ok(authentication);
    }

}
