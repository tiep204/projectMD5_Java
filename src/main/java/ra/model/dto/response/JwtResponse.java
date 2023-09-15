package ra.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse {
    private String token ;
    private String type = "Bearer";
    private String firstName;
    private String lastName;
    private String username;
    private String phoneNumber;
    private String email;
    private Date birthday;
    private String image;
    private String address;
    private Date createAt = new Date();
    private boolean status = true;
    private List<String> roles = new ArrayList<>();
}