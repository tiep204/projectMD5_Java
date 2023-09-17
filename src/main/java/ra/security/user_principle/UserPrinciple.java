package ra.security.user_principle;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ra.model.domain.User;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPrinciple implements UserDetails {
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    @JsonIgnore
    private String password;
    private String phoneNumber;
    private String email;
    private Date birthday;
    private String image;
    private String address;
    private Date createAt = new Date();
    private boolean status = true;
    private Collection<? extends GrantedAuthority> authorities;

    public static UserPrinciple build(User user){
        List<GrantedAuthority> list =  user.getRoles().stream().map(
                role-> new SimpleGrantedAuthority(role.getRoleName().name())
        ).collect(Collectors.toList());
        return UserPrinciple.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .password(user.getPassword())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .birthday(user.getBirthday())
                .image(user.getImage())
                .address(user.getAddress())
                .createAt(user.getCreateAt())
                .status(user.isStatus())
                .authorities(list).build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}