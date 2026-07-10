package nl.novi.eindopdrachtbackendapi.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "keycloak_id", unique = true)
    private String keycloakId;

    public UserEntity() {}

    public UserEntity(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getKeycloakId() { return keycloakId; }

    public void setKeycloakId(String keycloakId) { this.keycloakId = keycloakId; }
}
