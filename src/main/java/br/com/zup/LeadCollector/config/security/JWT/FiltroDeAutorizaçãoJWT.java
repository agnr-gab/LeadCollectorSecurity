package br.com.zup.LeadCollector.config.security.JWT;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class FiltroDeAutorizaçãoJWT extends BasicAuthenticationFilter {
    private JWTComponent jwtComponent;
    private UserDetailsService userDetailsService; // Liskov Substitution Principle (LSP)  diz que objetos podem ser substituídos por seus subtipos sem que isso afete a execução correta do programa


    public FiltroDeAutorizaçãoJWT(AuthenticationManager authenticationManager, JWTComponent jwtComponent, UserDetailsService userDetailsService) {
        super(authenticationManager);
        this.jwtComponent = jwtComponent;
        this.userDetailsService = userDetailsService;
    }
}
