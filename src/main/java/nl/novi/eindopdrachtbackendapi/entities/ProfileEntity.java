package nl.novi.eindopdrachtbackendapi.entities;

import jakarta.persistence.*;

@Entity
@Table (name = "profile")
public class ProfileEntity {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private UserEntity user;

    @Column(nullable = false)
    private String displayName;

    @Column(nullable = false)
    private int age;

    public ProfileEntity() {}

    public ProfileEntity(UserEntity user, String displayName, int age) {
        this.user = user;
        this.displayName = displayName;
        this.age = age;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
