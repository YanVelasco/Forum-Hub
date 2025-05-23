package br.com.forum_hub.domain.usuario;
import br.com.forum_hub.domain.perfil.Perfil;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="usuarios")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomeCompleto;
    private String email;
    private String senha;
    private String nomeDeUsuario;
    private String biografia;
    private String miniBiografia;
    private Boolean verificado;
    private String token;
    private LocalDateTime expiracaoToken;

   @ManyToMany(fetch = FetchType.EAGER)
   @JoinTable(
       name = "usuarios_perfis",
       joinColumns = @JoinColumn(name = "usuario_id"),
       inverseJoinColumns = @JoinColumn(name = "perfil_id")
   )
   private List<Perfil> perfis = new ArrayList<>();

    public Usuario(DadosCadastroUsuario dados, String senhaCriptografada, Perfil perfil) {
        this.nomeCompleto = dados.nomeCompleto();
        this.email = dados.email();
        this.senha = senhaCriptografada;
        this.nomeDeUsuario = dados.nomeUsuario();
        this.biografia = dados.biografia();
        this.miniBiografia = dados.miniBiografia();
        this.verificado = false;
        this.token = UUID.randomUUID().toString();
        this.expiracaoToken = LocalDateTime.now().plusMinutes(30);
        this.perfis.add(perfil);
    }

    public Usuario() {
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return perfis;
    }

    public Long getId(){
        return id;
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public String getNomeDeUsuario() {
        return nomeDeUsuario;
    }

    public String getBiografia() {
        return biografia;
    }

    public String getMiniBiografia() {
        return miniBiografia;
    }

    public String getToken() {
        return token;
    }

    public List<Perfil> getPerfis() {
        return perfis;
    }

    public void verificar() {
        if (expiracaoToken.isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token expirado");
        }
        this.verificado = true;
        this.token = null;
        this.expiracaoToken = null;
    }

}