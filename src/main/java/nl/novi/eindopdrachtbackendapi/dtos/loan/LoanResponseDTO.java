package nl.novi.eindopdrachtbackendapi.dtos.loan;

public class LoanResponseDTO {
    private Long id;
    private Long profileId;
    private Long contentId;
    private Boolean loanedOut;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProfileId() {
        return profileId;
    }

    public void setProfileId(Long profileId) {
        this.profileId = profileId;
    }

    public Long getContentId() {
        return contentId;
    }

    public void setContentId(Long contentId) {
        this.contentId = contentId;
    }

    public Boolean getLoanedOut() {
        return loanedOut;
    }

    public void setLoanedOut(Boolean loanedOut) {
        this.loanedOut = loanedOut;
    }
}
