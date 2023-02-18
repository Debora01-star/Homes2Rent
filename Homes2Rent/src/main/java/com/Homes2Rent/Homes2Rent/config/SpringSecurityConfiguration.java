package com.Homes2Rent.Homes2Rent.config;


import com.Homes2Rent.Homes2Rent.security.JwtRequestFilter;
import com.Homes2Rent.Homes2Rent.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SpringSecurityConfiguration {

    public final CustomUserDetailsService customUserDetailsService;

    private final JwtRequestFilter jwtRequestFilter;


    public SpringSecurityConfiguration(CustomUserDetailsService customUserDetailsService, JwtRequestFilter jwtRequestFilter) {
        this.customUserDetailsService = customUserDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder())
                .and()
                .build();
    }

    @Bean
    protected SecurityFilterChain filter (HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .httpBasic().disable()
                .cors().and()
                .authorizeRequests()
                // Wanneer je deze uncomments, staat je hele security open. Je hebt dan alleen nog een jwt nodig.
//                .antMatchers("/**").permitAll()
                .antMatchers(HttpMethod.POST, "/users").permitAll()
                .antMatchers(HttpMethod.GET,"/users").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST,"/users/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/users/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/annulering").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/annulering/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/boeking").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/boeking/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/factuur").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/factuur/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/role").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/role/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/upload").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/upload/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/woning").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/woning/**").hasRole("ADMIN")


                // Je mag meerdere paths tegelijk definieren
                .antMatchers("/annulering", "/boeking", "/factuur", "/woning").hasAnyRole("ADMIN", "USER")
                .antMatchers("/authenticated").authenticated()
                .antMatchers("/authenticate").permitAll()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }






}
