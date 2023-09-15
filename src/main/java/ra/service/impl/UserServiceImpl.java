package ra.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ra.model.domain.Role;
import ra.model.domain.RoleName;
import ra.model.domain.User;
import ra.model.dto.request.LoginRequest;
import ra.model.dto.request.RegisterRequest;
import ra.repository.UserRepository;
import ra.service.IRoleService;
import ra.service.IUserService;

import java.util.*;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private IRoleService roleService;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findByUserName(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User save(RegisterRequest form) {
        if (userRepository.existsByUsername(form.getUsername())){
            throw new RuntimeException("User is exists");
        }
        // lấy ra danh sách các quyền và chuyển thành đối tượng Users
        Set<Role> roles  = new HashSet<>();
        if (form.getRoles()==null||form.getRoles().isEmpty()){
            roles.add(roleService.findByRoleName(RoleName.ROLE_USER));
        }else {
            form.getRoles().stream().forEach(
                    role->{
                        switch (role){
                            case "admin":
                                roles.add(roleService.findByRoleName(RoleName.ROLE_ADMIN));
                            case "seller":
                                roles.add(roleService.findByRoleName(RoleName.ROLE_SELLER));
                            case "user":
                                roles.add(roleService.findByRoleName(RoleName.ROLE_USER));}}
            );
        }
        User users = User.builder()
                .firstName(form.getFirstName())
                .lastName(form.getLastName())
                .username(form.getUsername())
                .password(passwordEncoder.encode(form.getPassword()))
                .phoneNumber(form.getPhoneNumber())
                .email(form.getEmail())
                .birthday(form.getBirthday())
                .image(form.getImage())
                .address(form.getAddress())
                .createAt(new Date())
                .status(true)
                .roles(roles)
                .build();
        return userRepository.save(users);
    }
}