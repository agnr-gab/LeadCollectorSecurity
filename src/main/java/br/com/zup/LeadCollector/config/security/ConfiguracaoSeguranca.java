package br.com.zup.LeadCollector.config.security;

import br.com.zup.LeadCollector.config.security.JWT.FiltroDeAutenticacaoJWT;
import br.com.zup.LeadCollector.config.security.JWT.FiltroDeAutorizaçãoJWT;
import br.com.zup.LeadCollector.config.security.JWT.JWTComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class ConfiguracaoSeguranca extends WebSecurityConfigurerAdapter {

    @Autowired
    private JWTComponent jwtComponent;
    @Autowired
    private UserDetailsService userDetailsService;

    private static final String[] ENDPOINT_POST_PUBLICO = {
            "/usuario",
            "/leads"
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //super.configure(http); não vamos utilizar o metodo padrão personalizavel
        http.csrf().disable(); //preocupação do front
        http.cors().configurationSource(configurationSource());

        http.authorizeHttpRequests()
                .antMatchers(HttpMethod.POST, ENDPOINT_POST_PUBLICO)
                .permitAll()
                .anyRequest()
                .authenticated();
        http.sessionManagement().
                sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilter(new FiltroDeAutenticacaoJWT(jwtComponent, authenticationManager()));
        http.addFilter(new FiltroDeAutorizaçãoJWT(authenticationManager(), jwtComponent, userDetailsService));
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    @Bean
    CorsConfigurationSource configurationSource() {
        UrlBasedCorsConfigurationSource corsConfigurationSource = new UrlBasedCorsConfigurationSource();
        corsConfigurationSource.registerCorsConfiguration("/**", new CorsConfiguration()
                .applyPermitDefaultValues());
        return corsConfigurationSource;
    }

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
