package com.miki.animestylebackend.controller;

import com.miki.animestylebackend.dto.request.ChangePasswordRequest;
import com.miki.animestylebackend.dto.request.UpdateProfileRequest;
import com.miki.animestylebackend.dto.response.UserData;
import com.miki.animestylebackend.dto.response.UserDto;
import com.miki.animestylebackend.dto.page.PageData;
import com.miki.animestylebackend.mapper.UserMapper;
import com.miki.animestylebackend.model.User;
import com.miki.animestylebackend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController extends BaseController{

    private final UserService service;
    private final UserMapper mapper;

    @PatchMapping
    @Operation(summary = "Change password for current user (changePassword)")
    public ResponseEntity<?> changePassword(
            @RequestBody ChangePasswordRequest request,
            Principal connectedUser
    ) {
        service.changePassword(request, connectedUser);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/updateUser/{id}")
    @Operation(summary = "Update user (updateUser)")
    public ResponseEntity<UserDto> updateUser(
            @PathVariable("id") UUID id,
            @Valid @RequestBody UpdateProfileRequest profile
    ) {
        return ResponseEntity.ok(
                service.updateUser(id, profile)
        );
    }

    @GetMapping("/me")
    @Operation(summary = "Get current user (getCurrentUser)")
    public ResponseEntity<UserDto> getUserProfile() {
        return ResponseEntity.ok(
                service.getUserProfile(getCurrentUser().getId(), getCurrentUser())
        );
    }
    @GetMapping("/getUserByUsername")
    @Operation(summary = "Get user by username (getUserByUsername)")
    public ResponseEntity<UserDto> getUserByUsername(@RequestParam("username") String username) {
        return ResponseEntity.ok(mapper.toUserDto(service.getUserByUsername(username), "User found successfully"));
    }



    @PostMapping
    @Transactional
    @Operation(summary = "Update Account (saveUser)")
    public ResponseEntity<UserDto> saveUser(
            @Valid @RequestBody UpdateProfileRequest profile
    ) {
        User currentUser = getCurrentUser();
        return ResponseEntity.ok(
                service.save(profile, currentUser)
        );
    }

    @GetMapping("/getAllUsers")
    @Operation(summary = "Get all users (getAllUsers)")
    public ResponseEntity<PageData<UserData>> getUsers(
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        if(email != null) {
            return ResponseEntity.ok(service.getUsersByEmailContaining(email, page, size, getCurrentUser()));
        }
        return ResponseEntity.ok(
                service.getAllUsers(
                        page, size
                )
        );
    }


}
