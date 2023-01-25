package com.Homes2Rent.Homes2Rent.controller;


import com.Homes2Rent.Homes2Rent.dto.UserDto;
import com.Homes2Rent.Homes2Rent.dto.UserInputDto;
import com.Homes2Rent.Homes2Rent.exceptions.DuplicatedEntryException;
import com.Homes2Rent.Homes2Rent.exceptions.RecordNotFoundException;
import com.Homes2Rent.Homes2Rent.exceptions.UsernameNotFoundException;
import com.Homes2Rent.Homes2Rent.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.security.Principal;
import java.util.Collection;

@RestController

public class UserController {

        private final UserService service;


        public UserController(UserService service) {
                this.service = service;
        }

        @PostMapping("users")
        public ResponseEntity<String> createUser(@Valid @RequestBody UserDto userDto, BindingResult br) {

                if (br.hasErrors()) {
                        // something's wrong
                        StringBuilder sb = new StringBuilder();
                        for (FieldError fe : br.getFieldErrors()) {
                                sb.append(fe.getField() + ": ");
                                sb.append(fe.getDefaultMessage());
                                sb.append("\n");
                        }
                        return new ResponseEntity<>(sb.toString(), HttpStatus.BAD_REQUEST);
                }
                else {
                        // happy flow
                        Long createdId = service.createUser(userDto);

                        URI uri = URI.create(
                                ServletUriComponentsBuilder
                                        .fromCurrentContextPath()
                                        .path("/users/" + createdId).toUriString());
                        return ResponseEntity.created(uri).body("User created!");
                }
        }



        @PutMapping("users/{id}")
        public ResponseEntity<Object> updateUser(@PathVariable String id, @Valid @RequestBody UserInputDto dto) throws RecordNotFoundException, DuplicatedEntryException {
                UserDto user = service.updateUser(id, dto);
                return new ResponseEntity<>(user, HttpStatus.CREATED);
        }

        @DeleteMapping("users/{id}")
        public ResponseEntity<Object> deleteUser(@PathVariable String id) throws RecordNotFoundException {
                service.deleteUser(id);
                return ResponseEntity.status(HttpStatus.OK).body("User deleted");
        }

        @GetMapping("users")
        public ResponseEntity<Collection<UserDto>> getUsers() {
                Collection<UserDto> dtos;
                dtos = service.getAllUsers();
                if (!dtos.isEmpty()) {
                        return ResponseEntity.ok().body(dtos);
                } else {
                        return ResponseEntity.notFound().build();
                }
        }

        @GetMapping("users/{id}")
        public ResponseEntity<Object> getOneUser(@PathVariable String id) throws RecordNotFoundException, UsernameNotFoundException {
                UserDto user = service.getUser(id);
                return ResponseEntity.ok().body(user);
        }
}
