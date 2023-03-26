package com.example.irena.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.spring_boot_security_web.entity.UserEntity;
import uz.pdp.spring_boot_security_web.model.dto.receive.UserRegisterDTO;
import uz.pdp.spring_boot_security_web.service.UserService;

import java.io.IOException;

@Controller
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    @PostMapping("/add")
    public String addUser(
            @ModelAttribute UserRegisterDTO userRegisterDTO
    ) {
        UserEntity isSuccess = userService.add(userRegisterDTO);
        if (isSuccess!=null){
            return "verify";
        }else{
            return "redirect:/register";
        }
    }
    @GetMapping("/verify/{code}")
    public String verify(@PathVariable("code") String code){
        if (userService.enableUser(code)) {
            return "redirect:/";
        }
        return "redirect:/register";
    }
    @GetMapping("/editPhoto")
    public String savePage(){
        return "cabinet";
    }
    @PostMapping("/save")
    public String savePhoto(@RequestParam("file")MultipartFile file) throws IOException {
        UserEntity userEntity= (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userService.editUserEntity(userEntity.getId(),file );

        return "redirect:/";
    }
}
