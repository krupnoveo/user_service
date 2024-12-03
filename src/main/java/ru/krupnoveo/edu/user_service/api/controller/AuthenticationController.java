package ru.krupnoveo.edu.user_service.api.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.krupnoveo.edu.user_service.api.dto.request.AuthenticationRequest;
import ru.krupnoveo.edu.user_service.api.dto.request.CreateUserRequest;
import ru.krupnoveo.edu.user_service.api.dto.response.UserDetailsResponse;
import ru.krupnoveo.edu.user_service.api.dto.response.UserResponse;
import ru.krupnoveo.edu.user_service.service.AuthenticationService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<UserResponse> registerClient(@RequestBody CreateUserRequest registerUserDto) {
        var user = authenticationService.signUp(registerUserDto, "CLIENT");
        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + user.jwtToken())
                .body(user);
    }

    @PostMapping("/barber/signup")
    public ResponseEntity<UserResponse> registerBarber(@RequestBody CreateUserRequest registerUserDto) {
        var user = authenticationService.signUp(registerUserDto, "BARBER");
        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + user.jwtToken())
                .body(user);
    }

    @PostMapping("/administrator/signup")
    public ResponseEntity<UserResponse> registerAdministrator(@RequestBody CreateUserRequest registerUserDto) {
        var user = authenticationService.signUp(registerUserDto, "ADMINISTRATOR");
        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + user.jwtToken())
                .body(user);
    }

    @PostMapping("/signin")
    public ResponseEntity<UserResponse> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        var user = authenticationService.authenticate(authenticationRequest);
        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + user.jwtToken())
                .body(user);
    }

    @GetMapping("/verify")
    public ResponseEntity<UserDetailsResponse> verify(@RequestParam(value = "token") String token) {
        var res = authenticationService.isTokenValid(token);
        return ResponseEntity.ok().body(res);
    }
}
