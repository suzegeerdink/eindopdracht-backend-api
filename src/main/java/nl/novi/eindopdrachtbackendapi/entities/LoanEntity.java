package nl.novi.eindopdrachtbackendapi.entities;

import jakarta.persistence.*;

@Entity
@Table (name = "loan")
public class LoanEntity {
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
    private boolean loanedOut;

    public LoanEntity() {}

    public LoanEntity(ProfileEntity profile, ContentEntity content, boolean loanedOut) {
        this.profile = profile;
        this.content = content;
        this.loanedOut = loanedOut;
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

    public boolean isLoanedOut() {
        return loanedOut;
    }

    public void setLoanedOut(boolean loanedOut) {
        this.loanedOut = loanedOut;
    }
}
