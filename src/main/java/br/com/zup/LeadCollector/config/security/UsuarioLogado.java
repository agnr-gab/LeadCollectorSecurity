package br.com.zup.LeadCollector.config.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UsuarioLogado implements UserDetails {
    private String id;
    private String email;
    private String senha;

    public UsuarioLogado(String id, String email, String senha) {
        this.id = id;
        this.email = email;
        this.senha = senha;
    }

    public UsuarioLogado() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    // metodos obrigatórios

    @Override //quais as autorizações o usuário tem que ter
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override //comparar a senha no banco de dados
    public String getPassword() {
        return senha;
    }

    @Override //comparar o usuário no banco de dados
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
