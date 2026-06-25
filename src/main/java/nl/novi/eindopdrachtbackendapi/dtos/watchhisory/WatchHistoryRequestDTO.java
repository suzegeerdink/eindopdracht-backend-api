package nl.novi.eindopdrachtbackendapi.dtos.watchhisory;

import jakarta.validation.constraints.NotNull;

public class WatchHistoryRequestDTO {
    @NotNull
    Long profileId;
    @NotNull
    Long contentId;

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
