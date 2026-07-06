package nl.novi.eindopdrachtbackendapi.entities;

import jakarta.persistence.*;
import jakarta.persistence.GenerationType;

@Entity
@Table(name = "profile_settings")
public class ProfileSettingsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean autoplayEnabled;

    private String language;

    @OneToOne
    @JoinColumn(name = "profile_id", unique = true)
    private ProfileEntity profile;

    public ProfileSettingsEntity() {}

    public ProfileSettingsEntity(boolean autoplayEnabled, String language, ProfileEntity profile) {
        this.autoplayEnabled = autoplayEnabled;
        this.language = language;
        this.profile = profile;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isAutoplayEnabled() {
        return autoplayEnabled;
    }

    public void setAutoplayEnabled(boolean autoplayEnabled) {
        this.autoplayEnabled = autoplayEnabled;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public ProfileEntity getProfile() {
        return profile;
    }

    public void setProfile(ProfileEntity profile) {
        this.profile = profile;
    }
}
