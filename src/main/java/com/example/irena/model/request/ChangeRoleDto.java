package com.example.irena.model.request;

import com.example.irena.entity.Enum.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeRoleDto {
    private String role;
}
