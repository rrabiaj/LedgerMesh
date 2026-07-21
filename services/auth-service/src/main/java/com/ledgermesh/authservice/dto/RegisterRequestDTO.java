package main.java.com.ledgermesh.authservice.dto;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDTO {

        @NotBlank(message = "Email is required")
        @Email(message = "Email should be valid")
        private String Email;

        @NotBlank(message = "Password is required")
        @Size(min = 8, message = "Password must be at least 8 characters long")
        private String password;

        @NotBlank(message = "FirstName is required")
        private String firstName;

        @NotBlank(message = "LastName is required")
        private String lastName;
        
}
