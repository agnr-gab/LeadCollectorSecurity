package br.com.zup.LeadCollector.config.security.JWT;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class JWTComponent {
    private String segredo = "Testando123";
    private Long milissegundo = 60000l;

    public String gerarToken(String username, UUID id) {
        Date vencimento = new Date(System.currentTimeMillis() + milissegundo);
        String token = Jwts.builder()
                .setSubject(username)
                .claim("idUsuario", id)
                .setExpiration(vencimento)
                .signWith(SignatureAlgorithm.HS512, segredo.getBytes()).compact();

        return token;
    }
}