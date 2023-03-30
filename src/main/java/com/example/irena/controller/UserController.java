package com.example.irena.controller;

import com.example.irena.model.request.UserLoginDTO;
import com.example.irena.model.request.UserResetPasswordDTO;
import com.example.irena.model.request.Verification;
import com.example.irena.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDTO userLoginDTO) {
        return userService.login(userLoginDTO);
    }
    @PostMapping("/verify")
    public ResponseEntity<?> verify(@RequestBody Verification verification) {
       return userService.verifyCodeCheckerForRestorePassword(verification);
    }

    @PostMapping("/getVerificationCode")
    public ResponseEntity<?> getVerificationCodeForRestorePassword(@RequestBody Verification verification) {
        return userService.generateCodeForForgetPassword(verification);
    }
    @PostMapping("/resetPassword")
    public ResponseEntity<?> resetUserPassword(@RequestBody UserResetPasswordDTO userResetPasswordDTO) {
        return userService.resetPassword(userResetPasswordDTO);
    }
}
