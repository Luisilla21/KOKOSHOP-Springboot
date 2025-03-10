package com.sena.kokoshop.configuracion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsService usuarioService;

    public SecurityConfig(UserDetailsService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(usuarioService);
        auth.setPasswordEncoder(passwordEncoder);
        return auth;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/index", "/registro", "/login", "/forgot-password", "/reset-password",
                                "/catalogo", "/vistaProductos", "/nosotros", "/asesoria",
                                "/css/**", "/js/**", "/images/**", "/error**",
                                "/webjars/**", "/assets/**",
                                "/productos/imagen/**", "/catalogo/producto/**")
                        .permitAll()
                        // Change these to match the exact format of your roles
                        .requestMatchers("/ventas/**", "/productos/**").hasAnyRole("ADMIN", "EMPLEADO")
                        .requestMatchers("/empleados/**", "/dashboard/**","/usuarios/**").hasRole("ADMIN")
                        .requestMatchers("/empleado/**").hasRole("EMPLEADO")
                        .requestMatchers("/compra/**", "/cliente/**").hasRole("CLIENTE")                        
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/index", true)
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll())
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}