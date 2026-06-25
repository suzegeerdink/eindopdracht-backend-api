package nl.novi.eindopdrachtbackendapi.controllers;

import jakarta.validation.Valid;
import nl.novi.eindopdrachtbackendapi.dtos.loan.LoanRequestDTO;
import nl.novi.eindopdrachtbackendapi.dtos.loan.LoanResponseDTO;
import nl.novi.eindopdrachtbackendapi.services.LoanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loans")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping
    public ResponseEntity<LoanResponseDTO> createLoan(@Valid @RequestBody LoanRequestDTO loanRequestDTO) {
        LoanResponseDTO loanResponseDTO = loanService.createLoan(loanRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(loanResponseDTO);
    }

    @GetMapping
    public ResponseEntity<List<LoanResponseDTO>> getAllLoans() {
        List<LoanResponseDTO> getAllLoans = loanService.getAllLoans();
        return ResponseEntity.ok(getAllLoans);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanResponseDTO> getLoanById(@PathVariable Long id) {
        LoanResponseDTO getLoanById = loanService.getLoanById(id);
        return ResponseEntity.ok(getLoanById);
    }

    @GetMapping("/profile/{profileId}")
    public ResponseEntity<List<LoanResponseDTO>> getAllLoanByProfileId(@PathVariable Long profileId) {
        List<LoanResponseDTO> getAllProfileLoans = loanService.getAllLoansByProfileId(profileId);
        return ResponseEntity.ok(getAllProfileLoans);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LoanResponseDTO> updateLoan(@PathVariable Long id, @Valid @RequestBody LoanRequestDTO loanRequestDTO) {
        LoanResponseDTO updatedLoan = loanService.updateLoan(id, loanRequestDTO);
        return ResponseEntity.ok(updatedLoan);
    }

    @PutMapping("/{id}/return")
    public ResponseEntity<LoanResponseDTO> returnLoan(@PathVariable Long id) {
        LoanResponseDTO returnedLoan = loanService.returnLoan(id);
        return ResponseEntity.ok(returnedLoan);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLoan(@PathVariable Long id) {
        loanService.deleteLoan(id);
        return ResponseEntity.noContent().build();
    }
}
