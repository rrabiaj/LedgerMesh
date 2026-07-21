package main.java.com.ledgermesh.authservice.dto;

@Getter
@Setter
@NoArgsConstructor
@AllArgsContrustor
@Builder
public class AuthResponseDTO {

    private String token;
    private UUID userID;
    private String email;
    private String role;

}
