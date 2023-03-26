package com.example.irena.service;

import com.example.irena.entity.Enum.RoleEnum;
import com.example.irena.entity.UserEntity;
import com.example.irena.exception.RecordNotFountException;
import com.example.irena.exception.UserNotFountException;
import com.example.irena.model.ChangeRoleDto;
import com.example.irena.model.UserRegisterDTO;
import com.example.irena.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Qualifier("javasampleapproachMailSender")
    private final JavaMailSender javaMailSender;

    public boolean enableUser(String code) {
        UserEntity userEntity = userRepository.findByCode(code).orElseThrow(() -> new RecordNotFountException("This code live time end"));
        userEntity.setCode(null);
        userEntity.setEnabled(true);
        userRepository.save(userEntity);
        return true;
    }

    public List<UserEntity> getList() {
        return userRepository.findAll();
    }

    public UserEntity getById(int id) {
        Optional<UserEntity> byId = userRepository.findById(id);
        return byId.orElse(null);
    }

    public boolean delete(int id) {
        userRepository.findById(id).orElseThrow(() -> new UserNotFountException(" User not found"));
        ;
        userRepository.deleteById(id);
        return true;
    }

    public UserEntity add(UserRegisterDTO userRegisterDTO) {
        userRepository.findByEmail(userRegisterDTO.getEmail()).orElseThrow(() -> new IllegalArgumentException(String.format("email %s already exist", userRegisterDTO.getEmail())));
        String code = UUID.randomUUID().toString();
        UserEntity userEntity = UserEntity.of(userRegisterDTO);
        userEntity.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));
        userEntity.setCode(code);
        sendMail(
                userRegisterDTO.getEmail(),
                "Verify code for activate account ",
                "<a href='http://localhost:8080/api/user/verify/" + code + "'>  Confirmation </a>"
        );
        return userRepository.save(userEntity);
    }
    public boolean sendMail(String sendingEmail, String massage, String code) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("qweqq@gmail.com");
            message.setTo(sendingEmail);
            message.setSubject(massage);
            message.setText(code);
            javaMailSender.send(message);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }



    public void editUserRolePermission(int id, ChangeRoleDto changeRoleDto) {
        final List<String> ROLE_LIST = List.of("ADMIN", "USER", "SUPER_ADMIN");
        UserEntity user = userRepository.findById(id).orElseThrow(()-> new UserNotFountException("User nor found"));
        List<RoleEnum> roles = user.getRoles();
        if (ROLE_LIST.contains(changeRoleDto.getRole())){
            roles.add(RoleEnum.valueOf(changeRoleDto.getRole()));
            user.setRoles(roles);
            userRepository.save(user);
        }

    }

    public void deleteUserRolePermission(int id,  ChangeRoleDto changeRoleDto ) {
        UserEntity user = userRepository.findById(id).orElseThrow(()-> new UserNotFountException("User nor found"));
        List<RoleEnum> roles = user.getRoles();
        if (roles.contains(changeRoleDto.getRole())){
            roles.remove(RoleEnum.valueOf(changeRoleDto.getRole()));
            user.setRoles(roles);
            userRepository.save(user);
        }
    }
}
