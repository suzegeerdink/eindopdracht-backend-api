package nl.novi.eindopdrachtbackendapi.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "watch_history")
public class WatchHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "profile_id", nullable = false)
    private ProfileEntity profile;

    @ManyToOne
    @JoinColumn(name = "content_id", nullable = false)
    private ContentEntity content;

    @Column(nullable = false)
    private LocalDate watchDate;

    public WatchHistoryEntity() {}

    public WatchHistoryEntity(ProfileEntity profile, ContentEntity content, LocalDate watchDate) {
        this.profile = profile;
        this.content = content;
        this.watchDate = watchDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProfileEntity getProfile() {
        return profile;
    }

    public void setProfile(ProfileEntity profile) {
        this.profile = profile;
    }

    public ContentEntity getContent() {
        return content;
    }

    public void setContent(ContentEntity content) {
        this.content = content;
    }

    public LocalDate getWatchDate() {
        return watchDate;
    }

    public void setWatchDate(LocalDate watchDate) {
        this.watchDate = watchDate;
    }
}
