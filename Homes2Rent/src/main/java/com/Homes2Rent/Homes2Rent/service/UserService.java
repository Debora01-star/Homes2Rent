package com.Homes2Rent.Homes2Rent.service;


import com.Homes2Rent.Homes2Rent.dto.UserDto;
import com.Homes2Rent.Homes2Rent.dto.UserInputDto;
import com.Homes2Rent.Homes2Rent.exceptions.RecordNotFoundException;
import com.Homes2Rent.Homes2Rent.exceptions.UsernameNotFoundException;
import com.Homes2Rent.Homes2Rent.model.Authority;
import com.Homes2Rent.Homes2Rent.model.User;
import com.Homes2Rent.Homes2Rent.repository.UserRepository;
import com.Homes2Rent.Homes2Rent.util.RandomStringGenerator;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
    public class UserService {
    private final UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public List<UserDto> getUser() {
        List<UserDto> collection = new ArrayList<>();
        List<User> list = (List<User>) userRepository.findAll();
        for (User user : list) {
            collection.add(fromUser(user));
        }
        return collection;
    }

    public UserDto getUser(String username) throws UsernameNotFoundException {
        UserDto dto = new UserDto();
        Optional<User> user = userRepository.findById(username);
        if (user.isPresent()) {
            dto = fromUser(user.get());
        } else {
            throw new UsernameNotFoundException(username);
        }
        return dto;
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsById(username);
    }

    public String createUser(@Valid UserInputDto userDto, BindingResult principal) {
        String randomString = RandomStringGenerator.generateAlphaNumeric(20);
        userDto.setApikey(randomString);
        User newUser = userRepository.save(toUser(userDto));
        return newUser.getUsername();
    }

    public void deleteUser(String username) {
        userRepository.deleteById(username);
    }

    public UserDto updateUser(String username, @Valid UserInputDto newUser) {
        if (!userRepository.existsById(username)) throw new RecordNotFoundException ("no username found");
        User user = userRepository.findById(username).get();
        user.setPassword(newUser.getPassword());
        UserDto userdto =fromUser(user);
        userRepository.save(user);
        return userdto;
    }

    public Set<Authority> getAuthorities(String username) throws UsernameNotFoundException {
        if (!userRepository.existsById(username)) throw new UsernameNotFoundException(username);
        User user = userRepository.findById(username).get();
        UserDto userDto = fromUser(user);
        return userDto.getAuthorities();
    }

    public void addAuthority(String username, String authority) throws UsernameNotFoundException {

        if (!userRepository.existsById(username)) throw new UsernameNotFoundException(username);
        User user = userRepository.findById(username).get();
        user.addAuthority(new Authority(username, authority));
        userRepository.save(user);
    }


    public static UserDto fromUser(User user) {

        var dto = new UserDto();

        dto.username = user.getUsername();
        dto.password = user.getPassword();
        dto.enabled = user.isEnabled();
        dto.apikey = user.getApikey();
        dto.email = user.getEmail();
        dto.authorities = user.getAuthorities();

        return dto;
    }

    public User toUser(@Valid UserInputDto userDto) {

        var user = new User();

        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setEnabled(userDto.getEnabled());
        user.setApikey(userDto.getApikey());
        user.setEmail(userDto.getEmail());

        return user;
    }

    public List<UserDto> getAllUsers() {

        return null;
    }

    public Long createUser(UserDto userDto) {
        return null;
    }
}