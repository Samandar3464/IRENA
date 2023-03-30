//package com.example.irena.controller;
//
//import com.example.irena.entity.UserEntity;
//import com.example.irena.model.UserRegisterDTO;
//import com.example.irena.service.UserService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//
//@Controller
//@RequestMapping("/api/user")
//@RequiredArgsConstructor
//public class UserController {
//
//    private final UserService userService;
//    @PostMapping("/add")
//    public String addUser(
//            @ModelAttribute UserRegisterDTO userRegisterDTO
//    ) {
//        UserEntity isSuccess = userService.add(userRegisterDTO);
//        if (isSuccess!=null){
//            return "verify";
//        }else{
//            return "redirect:/register";
//        }
//    }
//    @GetMapping("/verify/{code}")
//    public String verify(@PathVariable("code") String code){
//        if (userService.enableUser(code)) {
//            return "redirect:/";
//        }
//        return "redirect:/register";
//    }
//}
