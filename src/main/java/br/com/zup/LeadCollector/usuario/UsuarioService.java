package br.com.zup.LeadCollector.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public Usuario salvarUsuario (Usuario usuario) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String senhaEscondida = passwordEncoder.encode(usuario.getSenha());

        usuario.setSenha(senhaEscondida);
        return usuarioRepository.save(usuario);
    }

    public void atualizarUsuario(Usuario usuario, String id) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);

        if (usuarioOptional.isEmpty()){
            throw new RuntimeException("Usuario não existe");
        }

        Usuario usuario1 = usuarioOptional.get();
        if (!usuario1.getEmail().equals(usuario1.getEmail())){
            usuario1.setEmail(usuario.getEmail());
        }

        String senhaEscondida = bCryptPasswordEncoder.encode(usuario.getSenha());
        usuario1.setSenha(senhaEscondida);
        usuarioRepository.save(usuario1);
    }
}
