package com.ledgermesh.budgetservice.controller;

import com.ledgermesh.budgetservice.dto.BudgetEvaluationRequestDTO;
import com.ledgermesh.budgetservice.dto.BudgetEvaluationResponseDTO;
import com.ledgermesh.budgetservice.dto.BudgetRequestDTO;
import com.ledgermesh.budgetservice.dto.BudgetResponseDTO;
import com.ledgermesh.budgetservice.service.BudgetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/budgets")
@RequiredArgsConstructor
public class BudgetController {

    private final BudgetService budgetService;

    @PostMapping
    public ResponseEntity<BudgetResponseDTO> createBudget(@Valid @RequestBody BudgetRequestDTO request) {
        BudgetResponseDTO response = budgetService.createBudget(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BudgetResponseDTO> getBudgetById(@PathVariable UUID id) {
        return ResponseEntity.ok(budgetService.getBudget(id));
    }

    @GetMapping
    public ResponseEntity<List<BudgetResponseDTO>> getBudgets(@RequestParam(required = false) UUID userId) {
        if (userId != null) {
            return ResponseEntity.ok(budgetService.getBudgetsByUserId(userId));
        } 
        
        return ResponseEntity.ok(budgetService.getAllBudgets());
        
    }

    @PutMapping("/{id}")
    public ResponseEntity<BudgetResponseDTO> updateBudget(@PathVariable UUID id, @Valid @RequestBody BudgetRequestDTO request) {
        return ResponseEntity.ok(budgetService.updateBudget(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBudget(@PathVariable UUID id) {
        budgetService.deleteBudget(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/evaluate")
    public ResponseEntity<BudgetEvaluationResponseDTO> evaluateBudget(@Valid @RequestBody BudgetEvaluationRequestDTO request) {
        BudgetEvaluationResponseDTO response = budgetService.evaluateTransaction(request);
        return ResponseEntity.ok(response);
    }

}
