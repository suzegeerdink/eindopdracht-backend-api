package nl.novi.eindopdrachtbackendapi.mappers;

import nl.novi.eindopdrachtbackendapi.dtos.watchhisory.WatchHistoryRequestDTO;
import nl.novi.eindopdrachtbackendapi.dtos.watchhisory.WatchHistoryResponseDTO;
import nl.novi.eindopdrachtbackendapi.entities.ContentEntity;
import nl.novi.eindopdrachtbackendapi.entities.ProfileEntity;
import nl.novi.eindopdrachtbackendapi.entities.WatchHistoryEntity;
import org.springframework.stereotype.Component;

@Component
public class WatchHistoryMapper {

    public WatchHistoryResponseDTO toDTO(WatchHistoryEntity watchHistory) {
        WatchHistoryResponseDTO dto = new WatchHistoryResponseDTO();
        dto.setId(watchHistory.getId());
        dto.setProfileId(watchHistory.getProfile().getId());
        dto.setContentId(watchHistory.getContent().getId());
        dto.setWatchDate(watchHistory.getWatchDate());
        return dto;
    }

    public WatchHistoryEntity toEntity(WatchHistoryRequestDTO dto, ContentEntity content, ProfileEntity profile) {
        WatchHistoryEntity watchHistory = new WatchHistoryEntity();
        watchHistory.setProfile(profile);
        watchHistory.setContent(content);
        return watchHistory;
    }
}
