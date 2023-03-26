package com.example.irena.adminController;

import com.example.irena.entity.UserEntity;
import com.example.irena.model.ChangeRoleDto;
import com.example.irena.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminControlUpUser {
    private final UserService userService;

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @GetMapping("/allUsers")
    public String getUserList(Model model) {
        List<UserEntity> userEntities = userService.getList();
        model.addAttribute("users", userEntities);
        return "admin/userPageForAdmin";
    }
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @GetMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable int id,Model model) {
        userService.delete(id);
        return "redirect:/admin/allUsers";
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PostMapping("/editUser/{id}")
    public String editUserRolePermission(@PathVariable int id, @ModelAttribute ChangeRoleDto role) {
        userService.editUserRolePermission(id,role);
        return "redirect:/admin/allUsers";
    }
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PostMapping("/deleteRPUser/{id}")
    public String deleteUserRolePermission(@PathVariable int id, @ModelAttribute ChangeRoleDto role) {
        userService.deleteUserRolePermission(id,role);
        return "redirect:/admin/allUsers";
    }
}
