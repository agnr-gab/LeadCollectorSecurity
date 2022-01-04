package br.com.zup.LeadCollector.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class ConfiguracaoSeguranca extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //super.configure(http); não vamos utilizar o metodo padrão personalizavel
        http.authorizeHttpRequests()
                .antMatchers(HttpMethod.GET, "/leads")
                .permitAll()
                .anyRequest()
                .authenticated();
    }
}
