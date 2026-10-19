package by.pavelkht.service_program_on_spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import static by.pavelkht.service_program_on_spring.database.entity.UserRole.ADMIN;
import static by.pavelkht.service_program_on_spring.database.entity.UserRole.USER;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/registration", "/v3/api-docs", "/swagger-ui/**").permitAll()
                        .requestMatchers("/users").hasAuthority(ADMIN.getAuthority())
                        .requestMatchers("/{id}/delete").hasAnyAuthority(ADMIN.getAuthority(), USER.getAuthority())
                        .anyRequest()
                        .authenticated())
                .formLogin(form ->
                        form
                                .loginPage("/login")
                                .defaultSuccessUrl("/user")
                                .permitAll())
                .logout(logout ->
                        logout
                                .logoutUrl("/logout")
                                .logoutSuccessUrl("/login")
                                .deleteCookies("JSESSIONID"));
        return http.build();
    }
}
