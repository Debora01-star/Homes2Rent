package com.Homes2Rent.Homes2Rent.security;

import com.Homes2Rent.Homes2Rent.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;



    @EnableWebSecurity
    public class SecurityConfig  {

        private final JwtRequestFilter jwtRequestFilter;
        private final JwtService jwtService;
        private final UserRepository userRepository;

        public SecurityConfig(JwtRequestFilter jwtRequestFilter, JwtService service, UserRepository userRepos) {
            this.jwtRequestFilter = jwtRequestFilter;
            this.jwtService = service;
            this.userRepository = userRepos;
        }

        @Bean
        public AuthenticationManager authManager(HttpSecurity http, PasswordEncoder encoder, UserDetailsService udService) throws Exception {
            return http.getSharedObject(AuthenticationManagerBuilder.class)
                    .userDetailsService(udService)
                    .passwordEncoder(encoder)
                    .and()
                    .build();
        }

        @Bean
        public UserDetailsService userDetailsService() {
            return new MyUserDetailsService(this.userRepository);
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http
                    .httpBasic().disable()
                    .authorizeRequests()
                    .antMatchers(HttpMethod.POST, "/users").permitAll()
                    .antMatchers(HttpMethod.POST, "/auth").permitAll()
                    .antMatchers("/secret").hasAuthority("ADMIN")
                    .antMatchers("/**").hasAnyAuthority("USER", "ADMIN")
                    .and()
                    .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                    .csrf().disable()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

            return http.build();
        }
    }


