package com.francisco.blog.config;

import jakarta.servlet.DispatcherType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final SecurityFilter securityFilter;





    public SecurityConfig(SecurityFilter securityFilter) {
        this.securityFilter = securityFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http){
        return http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configure(http))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .dispatcherTypeMatchers(DispatcherType.ERROR).permitAll()
                        .requestMatchers(HttpMethod.POST, "/user/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/user/register").permitAll()
                        .requestMatchers("/user/show").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/user/editUser").hasAnyRole("USER","ADMIN")
                        .requestMatchers("/user/updatePassword").hasAnyRole("USER","ADMIN")
                        .requestMatchers("/user/adminUpdatePassword").hasRole("ADMIN")
                        .requestMatchers("/user/softDeleteUser").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/user/softDeleteAdmin").hasRole("ADMIN")
                        .requestMatchers("/user/permDeleteAdmin").hasRole("ADMIN")
                        .requestMatchers("/post/create").hasAnyRole("USER","ADMIN")
                        .requestMatchers("/post/showAll").hasAnyRole("USER","ADMIN")
                        .requestMatchers("/post/showMyPost").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/post/editMyPost").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/post/deletePost").hasAnyRole("USER", "ADMIN")
                        .anyRequest().authenticated())
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authentication){
        return authentication.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
