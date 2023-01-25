package com.Homes2Rent.Homes2Rent.Payload;

public class AuthenticationResponse {


        private final String jwt;

        public AuthenticationResponse(String jwt) {
            this.jwt = jwt;
        }

        public String getJwt() {
            return jwt;
        }

    }

