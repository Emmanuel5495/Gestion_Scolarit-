package bf.ensp.scolarite.config;

import bf.ensp.scolarite.security.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Désactive CSRF car on utilise JWT (stateless)
            .csrf(AbstractHttpConfigurer::disable)

            // Définit les routes publiques et protégées
            .authorizeHttpRequests(auth -> auth
                // Routes publiques — pas besoin d'être connecté
                .requestMatchers(
                        "/api/auth/**",
                        "/api/modes-entree",
                        "/api/preinscriptions",
                        "/swagger-ui/**",
                        "/v3/api-docs/**"
                ).permitAll()

                // Routes réservées aux étudiants
                .requestMatchers("/api/etudiant/**")
                        .hasRole("ETUDIANT")

                // Routes réservées au service pédagogique
                .requestMatchers("/api/service-pedagogique/**")
                        .hasRole("SERVICE_PEDAGOGIQUE")

                // Routes réservées à l'admin
                .requestMatchers("/api/admin/**")
                        .hasRole("ADMIN")

                // Toutes les autres routes nécessitent d'être connecté
                .anyRequest().authenticated()
            )

            // Pas de session — on est stateless avec JWT
            .sessionManagement(session -> session
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            // Notre provider d'authentification
            .authenticationProvider(authenticationProvider)

            // Notre filtre JWT avant le filtre d'authentification par défaut
            .addFilterBefore(jwtAuthFilter,
                    UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
