package com.example.irena.adminController;

import com.example.irena.model.request.UserRegisterDTO;
import com.example.irena.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AdminControllerUpUser {

    private final UserService userService;

    @PostMapping("/add")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> addUser(@RequestBody UserRegisterDTO userRegisterDTO) {
        return userService.add(userRegisterDTO);
    }
    @GetMapping("/getList")
    @PreAuthorize("hasRole('SUPER_ADMIN') and hasRole('ADMIN')")
    public ResponseEntity<?> getUserList(){
        return userService.getList();
    }
    @GetMapping("/getById/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN') and hasRole('ADMIN')")
    public ResponseEntity<?> getUserById(@PathVariable int id){
        return userService.getById(id);
    }
    @DeleteMapping("/deleteById/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> deleteUserById(@PathVariable int id){
        return userService.delete(id);
    }

}
