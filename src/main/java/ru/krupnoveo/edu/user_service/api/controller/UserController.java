package ru.krupnoveo.edu.user_service.api.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.krupnoveo.edu.user_service.api.dto.request.UpdatePasswordRequest;
import ru.krupnoveo.edu.user_service.api.dto.request.UpdateUserRequest;
import ru.krupnoveo.edu.user_service.api.dto.response.ListUserResponse;
import ru.krupnoveo.edu.user_service.api.dto.response.UserResponse;
import ru.krupnoveo.edu.user_service.service.UserService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/me")
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserResponse> getUserById(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok().body(userService.getUserById(token.split(" ")[1]));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable UUID id) {
        return ResponseEntity.ok().body(userService.getUserById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<ListUserResponse> getAllUsers(
            @RequestParam (required = false) UUID barbershopId,
            @RequestParam (required = false) String role
    ) {
        return ResponseEntity.ok().body(
                new ListUserResponse(userService.getAllUsers(barbershopId, role))
        );
    }

    @DeleteMapping("/delete")
    public ResponseEntity<UserResponse> deleteUser(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok().body(userService.deleteUserByToken(token.split(" ")[1]));
    }

    @DeleteMapping("{id}/delete")
    public ResponseEntity<UserResponse> deleteUser(@PathVariable UUID id) {
        return ResponseEntity.ok().body(userService.deleteUserById(id));
    }

    @PutMapping("/update/data")
    public ResponseEntity<UserResponse> updateUser(
            @RequestBody UpdateUserRequest updateUserRequest,
            @RequestHeader("Authorization") String token
    ) {
        return ResponseEntity.ok().body(userService.updateUserByToken(updateUserRequest, token.split(" ")[1]));
    }

    @PutMapping("{id}/update/data")
    public ResponseEntity<UserResponse> updateUser(
            @RequestBody UpdateUserRequest updateUserRequest,
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok().body(userService.updateUserById(updateUserRequest, id));
    }

    @PutMapping("/update/password")
    public ResponseEntity<UserResponse> updatePassword(
            @RequestBody UpdatePasswordRequest updatePasswordRequest,
            @RequestHeader("Authorization") String token
    ) {
        return ResponseEntity.ok().body(userService.updatePasswordByToken(updatePasswordRequest, token.split(" ")[1]));
    }

    @PutMapping("{id}/update/password")
    public ResponseEntity<UserResponse> updatePassword(
            @RequestBody UpdatePasswordRequest updatePasswordRequest,
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok().body(userService.updatePasswordById(updatePasswordRequest, id));
    }

    @GetMapping("/photo")
    public ResponseEntity<InputStreamResource> getPhoto(
            @RequestHeader("Authorization") String token
    ) {
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(userService.getPhotoByToken(token.split(" ")[1]));
    }

    @GetMapping("{id}/photo")
    public ResponseEntity<InputStreamResource> getPhoto(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(userService.getPhotoById(id));
    }

    @PostMapping("/update/photo")
    public ResponseEntity<Void> setPhoto(
            @RequestParam(value = "photo") MultipartFile file,
            @RequestHeader("Authorization") String token
    ) {
        userService.setPhotoByToken(file, token.split(" ")[1]);
        return ResponseEntity.ok().build();
    }

    @PostMapping("{id}/update/photo")
    public ResponseEntity<Void> setPhoto(
            @RequestParam(value = "photo") MultipartFile file,
            @PathVariable UUID id
    ) {
        userService.setPhotoById(file, id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/photo/delete")
    public ResponseEntity<Void> deletePhoto(@RequestHeader("Authorization") String token) {
        userService.deletePhotoByToken(token.split(" ")[1]);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}/photo/delete")
    public ResponseEntity<Void> deletePhoto(@PathVariable UUID id) {
        userService.deletePhotoById(id);
        return ResponseEntity.ok().build();
    }
}
