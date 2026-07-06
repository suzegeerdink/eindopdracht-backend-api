package nl.novi.eindopdrachtbackendapi.controllers;

import jakarta.validation.Valid;
import nl.novi.eindopdrachtbackendapi.dtos.loan.LoanRequestDTO;
import nl.novi.eindopdrachtbackendapi.dtos.loan.LoanResponseDTO;
import nl.novi.eindopdrachtbackendapi.helpers.UrlHelper;
import nl.novi.eindopdrachtbackendapi.services.LoanService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/loans")
public class LoanController {

    private final LoanService loanService;
    private final UrlHelper urlHelper;

    public LoanController(LoanService loanService, UrlHelper urlHelper) {
        this.loanService = loanService;
        this.urlHelper = urlHelper;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<LoanResponseDTO> createLoan(@Valid @RequestBody LoanRequestDTO loanRequestDTO) {
        LoanResponseDTO createdLoan = loanService.createLoan(loanRequestDTO);
        URI location = urlHelper.getCurrentUrlWithId(createdLoan.getId());
        return ResponseEntity.created(location).body(createdLoan);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<LoanResponseDTO>> getAllLoans() {
        List<LoanResponseDTO> getAllLoans = loanService.getAllLoans();
        return ResponseEntity.ok(getAllLoans);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LoanResponseDTO> getLoanById(@PathVariable Long id) {
        LoanResponseDTO getLoanById = loanService.getLoanById(id);
        return ResponseEntity.ok(getLoanById);
    }

    @GetMapping("/profile/{profileId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<LoanResponseDTO>> getAllLoanByProfileId(@PathVariable Long profileId) {
        List<LoanResponseDTO> getAllProfileLoans = loanService.getAllLoansByProfileId(profileId);
        return ResponseEntity.ok(getAllProfileLoans);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LoanResponseDTO> updateLoan(@PathVariable Long id, @Valid @RequestBody LoanRequestDTO loanRequestDTO) {
        LoanResponseDTO updatedLoan = loanService.updateLoan(id, loanRequestDTO);
        return ResponseEntity.ok(updatedLoan);
    }

    @PutMapping("/{id}/return")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LoanResponseDTO> returnLoan(@PathVariable Long id) {
        LoanResponseDTO returnedLoan = loanService.returnLoan(id);
        return ResponseEntity.ok(returnedLoan);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteLoan(@PathVariable Long id) {
        loanService.deleteLoan(id);
        return ResponseEntity.noContent().build();
    }
}
