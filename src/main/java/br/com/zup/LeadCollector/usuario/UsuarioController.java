package br.com.zup.LeadCollector.usuario;

import br.com.zup.LeadCollector.config.security.UsuarioLogado;
import br.com.zup.LeadCollector.usuario.dtos.CadastroUsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario") //como terá apenas acesso ao dado de um usuario usar no singular
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void cadastrarUsuario(@RequestBody CadastroUsuarioDTO cadastroUsuarioDTO) {
        Usuario usuario = new Usuario(); //utilizar model mapper aqui
        usuario.setEmail(cadastroUsuarioDTO.getEmail()); //utilizar model mapper aqui
        usuario.setSenha(cadastroUsuarioDTO.getSenha()); //utilizar model mapper aqui
        /*falha de segurança: retornar a model retorna a senha do usuario
        return usuarioService.salvarUsuario(usuario);*/
        usuarioService.salvarUsuario(usuario);
    }

    @PutMapping() //não carregar mais infos sensiveis na URL
    public void atualizarUsuario(@RequestBody CadastroUsuarioDTO cadastroUsuarioDTO, Authentication authentication) {
        UsuarioLogado usuarioLogado = (UsuarioLogado) authentication.getPrincipal();

        Usuario usuario = new Usuario();
        usuario.setEmail(cadastroUsuarioDTO.getEmail());
        usuario.setSenha(cadastroUsuarioDTO.getSenha());

        usuarioService.atualizarUsuario(usuario, usuarioLogado.getId());
    }

}