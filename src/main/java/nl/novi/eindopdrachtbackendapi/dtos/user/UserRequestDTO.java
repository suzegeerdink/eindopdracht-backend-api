package nl.novi.eindopdrachtbackendapi.dtos.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import nl.novi.eindopdrachtbackendapi.enums.Role;

public class UserRequestDTO {
    @Email
    private String email;
    @NotBlank
    private String password;
    private Role role;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
