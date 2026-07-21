package main.java.com.ledgermesh.authservice.dto;

@Getter
@Setter
@NoArgsConstructor
@AllArgsContrustor
public class LoginRequestDTO {
    
    @NotBlank(messsage = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

}
