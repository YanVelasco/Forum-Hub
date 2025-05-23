package br.com.forum_hub.controller;

import br.com.forum_hub.domain.perfil.DadosPerfil;
import br.com.forum_hub.domain.usuario.DadosCadastroUsuario;
import br.com.forum_hub.domain.usuario.DadosListagemUsuario;
import br.com.forum_hub.domain.usuario.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class UsuarioController {

    private final UsuarioService service;
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService service, UsuarioService usuarioService) {
        this.service = service;
        this.usuarioService = usuarioService;
    }

    @PostMapping("/registrar")
    public ResponseEntity<DadosListagemUsuario> cadastrar(@RequestBody @Valid DadosCadastroUsuario dados, UriComponentsBuilder uriBuilder){
        var usuario = service.cadastrar(dados);
        var uri = uriBuilder.path("/usuarios/{nomeUsuario}").buildAndExpand(usuario.getNomeDeUsuario()).toUri();
        return ResponseEntity.created(uri).body(new DadosListagemUsuario(usuario));
    }

    @GetMapping("/verificar-conta")
    public ResponseEntity<String> verificarEmail(@RequestParam String codigo){
        usuarioService.verificarEmail(codigo);
        return ResponseEntity.ok("Email verificado com sucesso");
    }

    @PatchMapping("/adcionar-perfil/{userId}")
    public ResponseEntity<DadosListagemUsuario> adicionarPerfil(
            @RequestBody @Valid DadosPerfil dadosPerfil,
            @PathVariable Long userId
    ){
        var usuario = usuarioService.adicionarPerfil(userId, dadosPerfil);
        return ResponseEntity.ok(new DadosListagemUsuario(usuario));
    }

}
