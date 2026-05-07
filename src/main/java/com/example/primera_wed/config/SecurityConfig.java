package com.example.primera_wed.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.thymeleaf.extras.springsecurity6.dialect.SpringSecurityDialect;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Desactivamos temporalmente la protección CSRF para la consola H2 (si la usas)
                .csrf(csrf -> csrf.disable())
                .headers(headers -> headers.frameOptions(frame -> frame.disable()))

                .authorizeHttpRequests(auth -> auth
                        // 1. LO PÚBLICO: Cualquiera puede ver la tienda, contacto y recursos gráficos
                        .requestMatchers("/", "/index", "/contacto", "/publicidad", "/css/**", "/js/**", "/img/**", "/h2-console/**").permitAll()

                        // 2. MI BIBLIOTECA: Tienes que iniciar sesión (no importa el rol)
                        .requestMatchers("/perfil", "/compra").authenticated()

                        // 3. ZONA RESTRINGIDA: Solo tú (El Administrador) puedes entrar al CRUD y Métricas
                        .requestMatchers("/admin/**", "/gestion", "/metricas").hasRole("ADMINISTRADOR")

                        // 4. Cualquier otra ruta no especificada, pedirá login por seguridad
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login") // Le decimos que use tu diseño de login, no el feo por defecto de Spring
                        .defaultSuccessUrl("/", true) // Si el login es un éxito, te manda a la tienda
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/") // Al cerrar sesión te devuelve a la tienda
                        .permitAll()
                );

        return http.build();
    }

    // El Encriptador: Spring Security se niega a guardar contraseñas como "12345"
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SpringSecurityDialect springSecurityDialect() {
        return new SpringSecurityDialect();
    }
}