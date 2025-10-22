package com.nisal.moneymanager.service;

import com.nisal.moneymanager.dto.ExpenseDTO;
import com.nisal.moneymanager.dto.IncomeDTO;
import com.nisal.moneymanager.entity.CategoryEntiry;
import com.nisal.moneymanager.entity.ExpenseEntity;
import com.nisal.moneymanager.entity.IncomeEntity;
import com.nisal.moneymanager.entity.ProfileEntity;
import com.nisal.moneymanager.mapper.ExpenseMapper;
import com.nisal.moneymanager.mapper.IncomeMapper;
import com.nisal.moneymanager.repository.CategoryRepository;
import com.nisal.moneymanager.repository.IncomeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IncomeService {
    private final CategoryRepository categoryRepository;
    private final IncomeRepository incomeRepository;
    private final ProfileService profileService;

    public IncomeDTO addIncome(IncomeDTO dto) {
        ProfileEntity profile = profileService.getCurrentProfile();
        CategoryEntiry category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        IncomeEntity newIncome = IncomeMapper.toEntity(dto, profile, category);
        newIncome = incomeRepository.save(newIncome);
        return IncomeMapper.toDto(newIncome);
    }

    public List<IncomeDTO> getCurrentMonthIncomesForCurrentUser() {
        ProfileEntity profile = profileService.getCurrentProfile();
        LocalDate now = LocalDate.now();
        LocalDate startDate = now.withDayOfMonth(1);
        LocalDate endDate = now.withDayOfMonth(now.lengthOfMonth());
        List<IncomeEntity> list = incomeRepository.findByProfileIdAndDateBetween(profile.getId(), startDate, endDate);
        return list.stream().map(IncomeMapper::toDto).toList();
    }

    //delete income by is for current user
    public void deleteIncome(Long incomeId) {
        ProfileEntity profile = profileService.getCurrentProfile();
        IncomeEntity entity = incomeRepository.findById(incomeId)
                .orElseThrow(() -> new RuntimeException("Income not found"));
        if (!entity.getProfile().getId().equals(profile.getId())) {
            throw new RuntimeException("Unauthorized to delete this income");
        }
        incomeRepository.delete(entity);
    }

}
