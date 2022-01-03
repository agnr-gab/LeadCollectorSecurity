package br.com.zup.LeadCollector.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuario") //como terá apenas acesso ao dado de um usuario usar no singular
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

}
