package ra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ra.exception.LoginException;
import ra.model.dto.request.LoginRequest;
import ra.model.dto.request.RegisterRequest;
import ra.model.dto.response.JwtResponse;
import ra.security.jwt.JwtProvider;
import ra.security.user_principle.UserPrinciple;
import ra.service.IUserService;
import ra.service.impl.MailService;


import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v4/auth")
@CrossOrigin("*")
public class UserController {
    @Autowired
    private MailService mailService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private IUserService userService;

    @GetMapping
    public ResponseEntity<String> home() {
        return ResponseEntity.ok("vào được rồi nè");
    }

    @PostMapping("/sign-in")
    public ResponseEntity<JwtResponse> signin(@RequestBody LoginRequest loginRequest) throws LoginException {
        Authentication authentication = null;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            ); // tạo đối tương authentiction để xác thực thông qua username va password
            // tạo token và trả về cho người dùng
        } catch (AuthenticationException e) {
            throw new LoginException("Username or password is incorrect!");
        }
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        String token = jwtProvider.generateToken(userPrinciple);
        // lấy ra user principle
        List<String> roles = userPrinciple.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        return ResponseEntity.ok(JwtResponse.builder()
                .token(token)
                .firstName(userPrinciple.getFirstName())
                .lastName(userPrinciple.getLastName())
                .username(userPrinciple.getUsername())
                .phoneNumber(userPrinciple.getPhoneNumber())
                .email(userPrinciple.getEmail())
                .birthday(userPrinciple.getBirthday())
                .image(userPrinciple.getImage())
                .address(userPrinciple.getAddress())
                .createAt(userPrinciple.getCreateAt())
                .roles(roles)
                .type("Bearer")
                .status(userPrinciple.isStatus())
                .build());
    }

    @PostMapping("/sign-up")
    public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest) {
        userService.save(registerRequest); // save : status : pending
        mailService.sendMail(registerRequest.getEmail(), "Hacker Viet Nam", "<h1 style='color:red'>Chúc mừng bạn đã bị hack nick !</h1>");
        return new ResponseEntity("success", HttpStatus.CREATED);
    }
}