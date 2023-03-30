package com.example.irena.service;

import com.example.irena.entity.UserEntity;
import com.example.irena.exception.UserNotFountException;
import com.example.irena.jwtConfig.JwtGenerate;
import com.example.irena.model.request.UserLoginDTO;
import com.example.irena.model.request.UserRegisterDTO;
import com.example.irena.model.request.UserResetPasswordDTO;
import com.example.irena.model.request.Verification;
import com.example.irena.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.random.RandomGenerator;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final MailService mailService;

    public ResponseEntity<?> login(UserLoginDTO userLoginDTO) {
        try {
            Authentication authentication = new UsernamePasswordAuthenticationToken(userLoginDTO.getEmail(), userLoginDTO.getPassword());
            Authentication authenticate = authenticationManager.authenticate(authentication);
            String accessToken = "Bearer " + JwtGenerate.generateAccessToken((UserEntity) authenticate.getPrincipal());
            return ResponseEntity.ok(accessToken);
        } catch (BadCredentialsException e) {
            throw new UserNotFountException("User not found");
        }

    }

    public ResponseEntity<?> getList() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    public ResponseEntity<?> getById(int id) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new UserNotFountException("User not found"));
        return ResponseEntity.ok(userEntity);
    }

    public ResponseEntity<?> delete(int id) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new UserNotFountException(" User not found"));
        userEntity.setEnabled(false);
        userRepository.save(userEntity);
        return ResponseEntity.ok("User deleted");
    }

    public ResponseEntity<?> add(UserRegisterDTO userRegisterDTO) {
        Optional<UserEntity> byEmail = userRepository.findByEmail(userRegisterDTO.getEmail());
        if (byEmail.isPresent()) {
            throw new IllegalArgumentException(String.format("email %s already exist", userRegisterDTO.getEmail()));
        }
        UserEntity userEntity = UserEntity.of(userRegisterDTO);
        userEntity.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));
        return ResponseEntity.ok(userRepository.save(userEntity));
    }

    public ResponseEntity<?> generateCodeForForgetPassword(Verification verification) {
        UserEntity userEntity = userRepository.findByEmail(verification.getEmail()).orElseThrow(() -> new UserNotFountException("User not found"));
        int verificationCode = verificationCodeGenerator();
//        mailService.sendMail(
//                verification.getEmail(),
//                "Verify code for activate account ", "" + verificationCode
//        );
        userEntity.setCode(verificationCode);
        System.out.println(verificationCode);
        userRepository.save(userEntity);
        return ResponseEntity.ok("Verification code sent to your email");
    }

    public ResponseEntity<?> verifyCodeCheckerForRestorePassword(Verification verification) {
        UserEntity userEntity = userRepository.findByEmailAndCode(verification.getEmail(), verification.getCode()).orElseThrow(() -> new UserNotFountException("User not found"));
        userEntity.setCode(0);
        userRepository.save(userEntity);
        return ResponseEntity.ok("User verified successfully");
    }

    public ResponseEntity<?> resetPassword(UserResetPasswordDTO userRegisterDTO) {
        UserEntity userEntity = userRepository.findByEmail(userRegisterDTO.getEmail()).orElseThrow(() -> new IllegalArgumentException(String.format("email %s already exist", userRegisterDTO.getEmail())));
        userEntity.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));
        return ResponseEntity.ok(userRepository.save(userEntity));
    }

    private int verificationCodeGenerator() {
        return RandomGenerator.getDefault().nextInt(100000, 999999);
    }


//    public ResponseEntity<?> editUserRolePermission(int id, ChangeRoleDto changeRoleDto) throws RoleNotFoundException {
//        final List<String> ROLE_LIST = List.of("ADMIN", "USER", "SUPER_ADMIN");
//        UserEntity user = userRepository.findById(id).orElseThrow(() -> new UserNotFountException("User nor found"));
//        List<RoleEnum> roles = user.getRoles();
//        if (!ROLE_LIST.contains(changeRoleDto.getRole()) && roles.contains(changeRoleDto.getRole())) {
//            throw new RoleNotFoundException();
//        }
//        roles.add(RoleEnum.valueOf(changeRoleDto.getRole()));
//        user.setRoles(roles);
//        userRepository.save(user);
//        return ResponseEntity.ok("Role changed ");
//    }
//
//    public ResponseEntity<?> deleteUserRolePermission(int id, ChangeRoleDto changeRoleDto) throws RoleNotFoundException {
//        UserEntity user = userRepository.findById(id).orElseThrow(() -> new UserNotFountException("User nor found"));
//        List<RoleEnum> roles = user.getRoles();
//        if (!roles.contains(changeRoleDto.getRole())) {
//            throw new RoleNotFoundException();
//        }
//        roles.remove(RoleEnum.valueOf(changeRoleDto.getRole()));
//        user.setRoles(roles);
//        userRepository.save(user);
//        return ResponseEntity.ok("Role deleted ");
//    }
}
