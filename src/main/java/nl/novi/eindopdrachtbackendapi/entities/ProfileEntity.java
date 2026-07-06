package nl.novi.eindopdrachtbackendapi.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table (name = "profile")
public class ProfileEntity {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(nullable = false)
    private String displayName;

    @Column(nullable = false)
    private LocalDate birthDate;

    @OneToOne(mappedBy = "profile", cascade = CascadeType.ALL)
    private ProfileSettingsEntity settings;

    public ProfileEntity() {}

    public ProfileEntity(UserEntity user, String displayName, LocalDate birthDate) {
        this.user = user;
        this.displayName = displayName;
        this.birthDate = birthDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setSettings(ProfileSettingsEntity settings) {
        this.settings = settings;

        if (settings != null) {
            settings.setProfile(this);
        }
    }

    public ProfileSettingsEntity getSettings() {
        return settings;
    }
}
