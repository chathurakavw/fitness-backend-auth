package com.fitness.auth.controller;


import com.fitness.auth.dto.*;
import com.fitness.auth.entity.Role;
import com.fitness.auth.entity.User;
import com.fitness.auth.repository.RoleRepository;
import com.fitness.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @PostMapping(path = "/login")
    public ResponseDto<?> login(@RequestBody LoginDto loginDto) {
        Optional<User> userInfo = userRepository.findUserByUsername(loginDto.getUsername());
        if (userInfo.isPresent()) {
            User user = userInfo.get();
            if (user.getPassword().equals(loginDto.getPassword())) {
                LoginResponseDto loginResponseDto = new LoginResponseDto();
                loginResponseDto.setUserId(user.getId());
                Optional<Role> role = roleRepository.findById(user.getRoleId());
                role.ifPresent(value -> loginResponseDto.setRoleName(value.getName()));
                return ResponseDto.builder().status(true).data(loginResponseDto).build();
            }
        }
        return ResponseDto.builder().status(false).build();
    }

    @GetMapping(path = "/role-list")
    public ResponseDto<?> roleList() {
        List<Role> roleList = roleRepository.findAll();
        if (!roleList.isEmpty()) {
            return ResponseDto.builder().status(true).data(roleList).build();
        }
        return ResponseDto.builder().status(false).data(roleList).build();
    }

    @GetMapping(path = "/user-list")
    public ResponseDto<?> userList() {
        List<User> userList = userRepository.findAllByDeletedFalse();
        if (!userList.isEmpty()) {
            return ResponseDto.builder().status(true).data(userList).build();
        }
        return ResponseDto.builder().status(false).build();
    }

    @GetMapping(path = "/user/{userId}")
    public UserDto userList(@PathVariable int userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            UserDto userDto = new UserDto();
            userDto.setId(user.get().getId());
            userDto.setUsername(user.get().getUsername());
            userDto.setFullName(user.get().getFullName());
            userDto.setRoleId(user.get().getRoleId());
            return userDto;
        }
        return null;
    }

    @PostMapping(path = "/user-registration")
    public ResponseDto<?> login(@RequestBody UserRegistrationDto userRegistrationDto) {
        Optional<User> userInfo = userRepository.findUserByUsername(userRegistrationDto.getUsername());
        if (userInfo.isPresent()) {

            /*ResponseDto obj = new ResponseDto();
            obj.setStatus();
            obj.setMessage();*/ // this is a builder pattern

            return ResponseDto.builder().status(false).message("Already exist").build();
        } else {
            User user = new User();
            user.setUsername(userRegistrationDto.getUsername());
            user.setPassword(userRegistrationDto.getPassword());
            user.setFullName(userRegistrationDto.getFullName());
            user.setAge(userRegistrationDto.getAge());
            user.setDob(userRegistrationDto.getDob());
            user.setRoleId(1);
            userRepository.save(user);
            return ResponseDto.builder().status(true).message("User account has been successfully created").build();
        }
    }

    @PostMapping(path = "/account-creation")
    public ResponseDto<?> accountCreation(@RequestBody AccountCreationDto accountCreationDto) {
        Optional<User> userInfo = userRepository.findUserByUsername(accountCreationDto.getUsername());
        if (userInfo.isPresent()) {
            return ResponseDto.builder().status(false).message("Already exist").build();
        } else {
            User user = new User();
            user.setUsername(accountCreationDto.getUsername());
            user.setPassword("Abc123@#");
            user.setFullName(accountCreationDto.getFullName());
            user.setAge(accountCreationDto.getAge());
            user.setDob(accountCreationDto.getDob());
            user.setRoleId(accountCreationDto.getRoleId());
            userRepository.save(user);
            return ResponseDto.builder().status(true).message("User account has been successfully created").build();
        }
    }

    @PutMapping(path = "/user-update")
    public ResponseDto<?> updateUser(@RequestBody UserUpdateDto userUpdateDto) {
        Optional<User> userInfo = userRepository.findById(userUpdateDto.getId());
        if (userInfo.isPresent()) {
            User user = userInfo.get();
            user.setUsername(userUpdateDto.getUsername());
            user.setFullName(userUpdateDto.getFullName());
            user.setAge(userUpdateDto.getAge());
            user.setDob(userUpdateDto.getDob());
            user.setRoleId(userUpdateDto.getRoleId());
            userRepository.save(user);
            return ResponseDto.builder().status(true).message("User details has been successfully updated").build();
        } else {
            return ResponseDto.builder().status(false).build();
        }
    }

    @DeleteMapping(path = "/user-delete/{userId}")
    public ResponseDto<?> userDelete(@PathVariable int userId) {
        Optional<User> userInfo = userRepository.findById(userId);
        if (userInfo.isPresent()) {
            User user = userInfo.get();
            user.setDeleted(Boolean.TRUE);
            userRepository.save(user);
            return ResponseDto.builder().status(true).message("User has been successfully deactivated").build();
        } else {
            return ResponseDto.builder().status(false).build();
        }
    }
}
