package com.francisco.blog.controller;

import com.francisco.blog.config.JWTUserData;
import com.francisco.blog.config.TokenConfig;
import com.francisco.blog.dto.request.DeleteUserRequest;
import com.francisco.blog.dto.request.LoginRequest;
import com.francisco.blog.dto.request.PasswordRequest;
import com.francisco.blog.dto.request.RegisterRequest;
import com.francisco.blog.dto.response.*;
import com.francisco.blog.entitys.User;
import com.francisco.blog.entitys.UserRole;
import com.francisco.blog.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@AllArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenConfig tokenConfig;

    @GetMapping("/show")
    public Page<ShowUserResponse> showAll(@PageableDefault(size = 10, sort = "username") Pageable pageable, Authentication authentication,boolean showDesactive){
        JWTUserData userData = (JWTUserData) authentication.getPrincipal();

        return userService.showAll(userData.userId(), pageable, showDesactive);
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> RegisterUser(@RequestBody RegisterRequest registerRequest){
        User user = new User();
        user.setUsername(registerRequest.username());
        user.setEmail(registerRequest.email());
        user.setUserRole(Set.of(UserRole.ROLE_USER));

        user.setPassword(passwordEncoder.encode(registerRequest.password()));



        userService.saveUser(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(new RegisterResponse(user.getUsername(), user.getEmail()));
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@Valid @RequestBody LoginRequest loginRequest){
        UsernamePasswordAuthenticationToken usenameAndPass = new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password());
        Authentication authentication = authenticationManager.authenticate(usenameAndPass);

        User user = (User) authentication.getPrincipal();
        String token = tokenConfig.generateToken(user);

        return ResponseEntity.ok(new LoginResponse(token));
    }

    @PutMapping("/editUser")
    public ResponseEntity<User> editUser( @RequestBody User user,@RequestParam Long id, Authentication authentication){
        JWTUserData userData = (JWTUserData) authentication.getPrincipal();

        Long idLogado = userData.userId();

        User updatedUser = userService.editUserById(idLogado, id, user);
        return ResponseEntity.ok(updatedUser);
    }
    @PutMapping("/updatePassword")
    public ResponseEntity<PasswordResponse> updatePassword(@RequestBody PasswordRequest password){
        UsernamePasswordAuthenticationToken usernameAndPass = new UsernamePasswordAuthenticationToken(password.email(), password.oldPassword());
        Authentication authentication = authenticationManager.authenticate(usernameAndPass);

        User user = (User) authentication.getPrincipal();

        String token = tokenConfig.generateToken(user);

        userService.EditPasswordById(user.getId(), user.getId(), password.password());
        return ResponseEntity.ok(new PasswordResponse(user.getUsername(), user.getEmail(), token));
    }
    @PutMapping("/adminUpdatePassword")
    public ResponseEntity<PasswordResponse> updatePassword(Authentication authentication ,@RequestParam String password, @RequestParam Long id){
        JWTUserData userData = (JWTUserData) authentication.getPrincipal();

        User user = userService.EditPasswordById(userData.userId(), id, password);

        return ResponseEntity.ok(new PasswordResponse(user.getUsername(), user.getEmail(), null));

    }
    @PutMapping("/softDeleteUser")
    public ResponseEntity<DeleteUserResponse> softDeleteUser(@RequestBody DeleteUserRequest deleteUserRequest){
        UsernamePasswordAuthenticationToken  userAndPass = new UsernamePasswordAuthenticationToken(deleteUserRequest.email(), deleteUserRequest.password());
        Authentication authentication = authenticationManager.authenticate(userAndPass);

        User user = (User) authentication.getPrincipal();

        DeleteUserResponse userDeleted = userService.SoftDeleteUserById(user.getId(), user.getId(), deleteUserRequest.reason(), deleteUserRequest.time());

        return ResponseEntity.ok(new DeleteUserResponse(userDeleted.username(), userDeleted.email(), userDeleted.is_Active(), userDeleted.reason(), userDeleted.time() + " days"));

    }
    @PutMapping("/softDeleteAdmin")
    public ResponseEntity<DeleteUserResponse> softDeleteUser(Authentication authentication, @RequestParam Long excludedId, @RequestParam String reason, @RequestParam Integer time){
        JWTUserData userData = (JWTUserData) authentication.getPrincipal();

        DeleteUserResponse userDeleted = userService.SoftDeleteUserById(userData.userId(), excludedId, reason, time);

        return ResponseEntity.ok(new DeleteUserResponse(userDeleted.username(), userDeleted.email(), userDeleted.is_Active(), userDeleted.reason(), userDeleted.time() + " days"));
    }
    @PutMapping("/permDeleteAdmin")
    public ResponseEntity<Void> permDeleteAdmin(@RequestParam Long id){

        userService.DeletePermUserById(id);

        return ResponseEntity.ok().build();
    }

}
