package nl.novi.eindopdrachtbackendapi.services;

import nl.novi.eindopdrachtbackendapi.dtos.loan.LoanRequestDTO;
import nl.novi.eindopdrachtbackendapi.dtos.loan.LoanResponseDTO;
import nl.novi.eindopdrachtbackendapi.entities.ContentEntity;
import nl.novi.eindopdrachtbackendapi.entities.LoanEntity;
import nl.novi.eindopdrachtbackendapi.entities.ProfileEntity;
import nl.novi.eindopdrachtbackendapi.mappers.LoanMapper;
import nl.novi.eindopdrachtbackendapi.repositories.ContentRepository;
import nl.novi.eindopdrachtbackendapi.repositories.LoanRepository;
import nl.novi.eindopdrachtbackendapi.repositories.ProfileRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoanService {
    private final LoanRepository loanRepository;
    private final ProfileRepository profileRepository;
    private final ContentRepository contentRepository;
    private final LoanMapper loanMapper;

    public LoanService(LoanRepository loanRepository,
                       ProfileRepository profileRepository,
                       ContentRepository contentRepository,
                       LoanMapper loanMapper) {
        this.loanRepository = loanRepository;
        this.profileRepository = profileRepository;
        this.contentRepository = contentRepository;
        this.loanMapper = loanMapper;
    }

    @Transactional(readOnly = true)
    public LoanResponseDTO getLoanById(Long id) {
        LoanEntity loan = loanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Loan not found"));
        return loanMapper.toDTO(loan);
    }

    @Transactional(readOnly = true)
    public List<LoanResponseDTO> getAllLoans() {
        List<LoanEntity> loans = loanRepository.findAll(Sort.by("id"));
        return loans.stream()
                .map(loanMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<LoanResponseDTO> getAllLoansByProfileId(Long profileId) {
        List<LoanEntity> loansProfile = loanRepository.findAllByProfileId(profileId);
        return loansProfile.stream()
                .map(loanMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public LoanResponseDTO createLoan(LoanRequestDTO dto) {
        ProfileEntity profile = profileRepository.findById(dto.getProfileId())
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        ContentEntity content = contentRepository.findById(dto.getContentId())
                .orElseThrow(() -> new RuntimeException("Content not found"));

        LoanEntity loan = loanMapper.toEntity(dto, content, profile);
        LoanEntity createdLoan = loanRepository.save(loan);
        return loanMapper.toDTO(createdLoan);
    }

    @Transactional
    public LoanResponseDTO updateLoan(Long id, LoanRequestDTO dto) {
        LoanEntity loan = loanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        ProfileEntity profile = profileRepository.findById(dto.getProfileId())
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        ContentEntity content = contentRepository.findById(dto.getContentId())
                .orElseThrow(() -> new RuntimeException("Content not found"));

        loan.setContent(content);
        loan.setProfile(profile);

        LoanEntity updatedLoan = loanRepository.save(loan);
        return loanMapper.toDTO(updatedLoan);
    }

    @Transactional
    public LoanResponseDTO returnLoan(Long id) {
        LoanEntity loan = loanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        loan.setLoanedOut(false);
        LoanEntity updatedLoan = loanRepository.save(loan);
        return loanMapper.toDTO(updatedLoan);
    }

    @Transactional
    public void deleteLoan(Long id) {
        if (!loanRepository.existsById(id)) {
            throw new RuntimeException("Loan not found");
        }
        loanRepository.deleteById(id);
    }
}
