package nl.novi.eindopdrachtbackendapi.mappers;

import nl.novi.eindopdrachtbackendapi.dtos.loan.LoanRequestDTO;
import nl.novi.eindopdrachtbackendapi.dtos.loan.LoanResponseDTO;
import nl.novi.eindopdrachtbackendapi.entities.ContentEntity;
import nl.novi.eindopdrachtbackendapi.entities.LoanEntity;
import nl.novi.eindopdrachtbackendapi.entities.ProfileEntity;
import org.springframework.stereotype.Component;

@Component
public class LoanMapper {

    public LoanResponseDTO toDTO(LoanEntity loan) {
        LoanResponseDTO dto = new LoanResponseDTO();
        dto.setId(loan.getId());
        dto.setProfileId(loan.getProfile().getId());
        dto.setContentId(loan.getContent().getId());
        dto.setLoanedOut(loan.isLoanedOut());
        return dto;
    }

    public LoanEntity toEntity(LoanRequestDTO dto, ContentEntity content, ProfileEntity profile) {
        LoanEntity loan = new LoanEntity();
        loan.setProfile(profile);
        loan.setContent(content);
        loan.setLoanedOut(dto.getLoanedOut());
        return loan;
    }
}
