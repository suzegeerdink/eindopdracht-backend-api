package nl.novi.eindopdrachtbackendapi.dtos.loan;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class LoanRequestDTO {
    @NotNull
    private Long profileId;
    @NotNull
    private Long contentId;

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
}
