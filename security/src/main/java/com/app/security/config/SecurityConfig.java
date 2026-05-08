package com.app.security.config;

import com.app.security.filters.JwtRequestFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
                .cors(withDefaults())
                .csrf(csrf->csrf.disable())
                .authorizeHttpRequests(auth->
                                auth.requestMatchers("/admin/**").hasRole("ADMIN")
                                        .requestMatchers("/user/**").hasRole("USER")
                                        .requestMatchers("/login","/role/**").permitAll()
                                        .anyRequest().authenticated()
                )
                .exceptionHandling(ex->
                        ex
                                .authenticationEntryPoint((req,res,authException)->{
                                    res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                                    res.getWriter().write("Unauthorized::Missing or Invalid token");
                                })
                                .accessDeniedHandler((req,res,accessDeniedException)->{
                                    res.setStatus(HttpServletResponse.SC_FORBIDDEN);
                                    res.getWriter().write("Forbidden::You don't have permissions");
                                })
                )
                .formLogin(formLogin->formLogin.disable())
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(sessionMgt->sessionMgt.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }
}
