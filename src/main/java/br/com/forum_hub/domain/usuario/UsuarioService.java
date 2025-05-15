package br.com.forum_hub.domain.usuario;

import br.com.forum_hub.domain.perfil.PerfilRepository;
import br.com.forum_hub.domain.perfil.enums.PerfilNome;
import br.com.forum_hub.infra.email.EmailService;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder encoder;
    private final EmailService emailService;
    private final PerfilRepository perfilRepository;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder encoder, EmailService emailService, PerfilRepository perfilRepository) {
        this.usuarioRepository = usuarioRepository;
        this.encoder = encoder;
        this.emailService = emailService;
        this.perfilRepository = perfilRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByEmailIgnoreCaseAndVerificadoTrue(username)
                .orElseThrow(() -> new UsernameNotFoundException("O usuário não foi encontrado ou não foi verificado"));
    }

    @Transactional
    public Usuario cadastrar( DadosCadastroUsuario dados) {
        var senhaCriptografada = encoder.encode(dados.senha());
        var perfil = perfilRepository.findByPerfilNome(PerfilNome.ESTUDANTE);
        var usuario = new Usuario(dados, senhaCriptografada, perfil);
        emailService.enviarEmailVerificacao(usuario);
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public void verificarEmail(String codigo) {
        var usuario = usuarioRepository.findByToken(codigo).orElseThrow(
                () -> new RuntimeException("Usuário não encontrado")
        );
        usuario.verificar();
    }

}