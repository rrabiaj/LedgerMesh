package com.ledgermesh.budgetservice.service;

import com.ledgermesh.budgetservice.dto.BudgetRequestDTO;
import com.ledgermesh.budgetservice.dto.BudgetResponseDTO;
import com.ledgermesh.budgetservice.exception.BudgetAlreadyExistsException;
import com.ledgermesh.budgetservice.exception.BudgetNotFoundException;
import com.ledgermesh.budgetservice.mapper.BudgetMapper;
import com.ledgermesh.budgetservice.model.Budget;
import com.ledgermesh.budgetservice.repository.BudgetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ledgermesh.budgetservice.dto.BudgetEvaluationRequestDTO;
import com.ledgermesh.budgetservice.dto.BudgetEvaluationResponseDTO;

import java.math.BigDecimal;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class BudgetService{

    private final BudgetRepository budgetRepository;
    private final BudgetMapper budgetMapper;

    public BudgetResponseDTO createBudget(BudgetRequestDTO request){

        String normalizedCategory = normalizeCategory(request.getCategory());

        budgetRepository.findByUserIdAndCategory(request.getUserId(), normalizedCategory).ifPresent(existingBudget -> {
            throw new BudgetAlreadyExistsException(request.getUserId(), normalizedCategory);
        });

        request.setCategory(normalizedCategory);
        Budget budget = budgetMapper.toEntity(request);
        @SuppressWarnings("null")
        Budget savedBudget = budgetRepository.save(budget);
        return budgetMapper.toResponse(savedBudget);
    }

    @Transactional(readOnly = true)
    public BudgetResponseDTO getBudget(UUID id){
        @SuppressWarnings("null")
        Budget budget = budgetRepository.findById(id)
                .orElseThrow(() -> new BudgetNotFoundException(id));
        return budgetMapper.toResponse(budget);
    }

    @Transactional(readOnly = true)
    public List<BudgetResponseDTO> getAllBudgets(){
        return budgetRepository.findAll().stream()
                .map(budgetMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<BudgetResponseDTO> getBudgetsByUserId(UUID userId){
        return budgetRepository.findByUserId(userId).stream()
                .map(budgetMapper::toResponse)
                .toList();
    }

    public BudgetResponseDTO updateBudget(UUID id, BudgetRequestDTO request){
        Budget budget = findBudgetById(id);
        String normalizedCategory = normalizeCategory(request.getCategory());

        budgetRepository.findByUserIdAndCategory(request.getUserId(), normalizedCategory)
                .filter(existingBudget -> !existingBudget.getId().equals(id))
                .ifPresent(existingBudget -> {
                    throw new BudgetAlreadyExistsException(request.getUserId(), normalizedCategory);
                });
        
        budget.setUserId(request.getUserId());
        budget.setCategory(normalizedCategory);
        budget.setLimitAmount(request.getLimitAmount());
        Budget updatedBudget = budgetRepository.save(budget);
        return budgetMapper.toResponse(updatedBudget);

    }

    public BudgetEvaluationResponseDTO evaluateTransaction(BudgetEvaluationRequestDTO request) {
        String normalizedCategory = normalizeCategory(request.getCategory());

        Budget budget = budgetRepository.findByUserIdAndCategory(request.getUserId(), normalizedCategory)
                .orElseThrow(() -> new RuntimeException("Budget not found for user: " + request.getUserId() + " and category: " + normalizedCategory));

        BigDecimal previousSpentAmount = budget.getSpentAmount();
        BigDecimal currentSpentAmount = previousSpentAmount.add(request.getAmount());

        budget.setSpentAmount(currentSpentAmount);
        Budget updatedBudget = budgetRepository.save(budget);

        BigDecimal remainingAmount = budget.getLimitAmount().subtract(updatedBudget.getSpentAmount());
        boolean exceeded = updatedBudget.getSpentAmount().compareTo(updatedBudget.getLimitAmount()) > 0;

        return BudgetEvaluationResponseDTO.builder()
                .budgetId(updatedBudget.getId())
                .userId(updatedBudget.getUserId())
                .category(updatedBudget.getCategory())
                .limitAmount(updatedBudget.getLimitAmount())
                .previousSpentAmount(previousSpentAmount)
                .currentSpentAmount(currentSpentAmount)
                .remainingAmount(remainingAmount)
                .exceeded(exceeded1)
                .build();
    }

    @SuppressWarnings("null")
    public void deleteBudget(UUID id){
        Budget budget = findBudgetById(id);
        budgetRepository.delete(budget);
    }

    @SuppressWarnings("null")
    private Budget findBudgetById(UUID id){
        return budgetRepository.findById(id)
                .orElseThrow(() -> new BudgetNotFoundException(id));
    }

    private String normalizeCategory(String category) {
        return category.trim().toUpperCase();
    }

}