package br.com.zup.LeadCollector.config.security.JWT;

import br.com.zup.LeadCollector.config.security.JWT.exceptions.TokenInvalidoException;
import io.jsonwebtoken.Claims;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FiltroDeAutorizaçãoJWT extends BasicAuthenticationFilter {
    private JWTComponent jwtComponent;
    private UserDetailsService userDetailsService; // Liskov Substitution Principle (LSP)  diz que objetos podem ser
    // substituídos por seus subtipos sem que isso afete a execução correta do programa


    public FiltroDeAutorizaçãoJWT(AuthenticationManager authenticationManager, JWTComponent jwtComponent,
                                  UserDetailsService userDetailsService) {
        super(authenticationManager);
        this.jwtComponent = jwtComponent;
        this.userDetailsService = userDetailsService;
    }

    public UsernamePasswordAuthenticationToken pegarAutentificacao(String token) {
        if (!jwtComponent.tokenValido(token)) {
            throw new TokenInvalidoException();
        }

        Claims claims = jwtComponent.pegarClaims(token);
        UserDetails usuarioLogado = userDetailsService.loadUserByUsername(claims.getSubject());

        return new UsernamePasswordAuthenticationToken(usuarioLogado, null, usuarioLogado.getAuthorities());

    }

    // filter chain por onde as autorizações passam
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        String token = request.getHeader("Authorization"); //autorizando a passagem do usuário por meio do token.
        // Pegando o token que fica no header na opção "Authorization".

        //"Token é o padrão estipulado
        if (token != null && token.startsWith("Token")) {
            try {
                UsernamePasswordAuthenticationToken authenticationToken = pegarAutentificacao(token.substring(6));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                //A palavra token é preciso ser retirada, pois não faz parte do token foi adicionada para
                // manter o padrão do HTTP, pois existem vários tipos de token e precisa ser informado o tipo usado,
                // senão irá impedir o momento de descriptografar. (token.substring(6)
            } catch (TokenInvalidoException exception) {
                response.sendError(HttpStatus.FORBIDDEN.value());
            }

        }

        // filter chain por onde as autorizações passam
        chain.doFilter(request, response);

    }
}
