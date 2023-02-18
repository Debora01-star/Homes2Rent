package com.Homes2Rent.Homes2Rent.controller;

import com.Homes2Rent.Homes2Rent.Payload.AuthenticationRequest;
import com.Homes2Rent.Homes2Rent.Payload.AuthenticationResponse;
import com.Homes2Rent.Homes2Rent.service.CustomUserDetailsService;
import com.Homes2Rent.Homes2Rent.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


    @CrossOrigin
    @RestController
    public class AuthenticationController {

        private final AuthenticationManager authenticationManager;

        private final CustomUserDetailsService userDetailsService;

        JwtUtil jwtUtl;

        public AuthenticationController(AuthenticationManager authenticationManager, CustomUserDetailsService userDetailsService, JwtUtil jwtUtl) {
            this.authenticationManager = authenticationManager;
            this.userDetailsService = userDetailsService;
            this.jwtUtl = jwtUtl;
        }

        @GetMapping(value = "/authenticated")
        public ResponseEntity<Object> authenticated(Authentication authentication, Principal principal) {
            return ResponseEntity.ok().body(principal);
        }

        @PostMapping(value = "/authenticate")
        public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception, BadCredentialsException {

            String username = authenticationRequest.getUsername();
            String password = authenticationRequest.getPassword();

            try {
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(username, password)
                );
            }
            catch (BadCredentialsException ex) {
                throw new Exception("Incorrect username or password", ex);
            }

            final UserDetails userDetails = userDetailsService
                    .loadUserByUsername(username);

            final String jwt = jwtUtl.generateToken(userDetails);

            return ResponseEntity.ok(new AuthenticationResponse(jwt));
        }

    }

