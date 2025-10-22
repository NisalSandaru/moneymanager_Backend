package com.nisal.moneymanager.mapper;

import com.nisal.moneymanager.dto.ExpenseDTO;
import com.nisal.moneymanager.entity.CategoryEntiry;
import com.nisal.moneymanager.entity.ExpenseEntity;
import com.nisal.moneymanager.entity.ProfileEntity;

public class ExpenseMapper {
    public static ExpenseEntity toEntity(ExpenseDTO dto, ProfileEntity profile, CategoryEntiry category) {
        return ExpenseEntity.builder()
                .name(dto.getName())
                .icon(dto.getIcon())
                .amount(dto.getAmount())
                .date(dto.getDate())
                .profile(profile)
                .category(category)
                .build();
    }

    public static ExpenseDTO toDto(ExpenseEntity entity) {
        return ExpenseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .icon(entity.getIcon())
                .categoryId(entity.getCategory() != null? entity.getCategory().getId(): null)
                .categoryName(entity.getCategory() != null? entity.getCategory().getName(): "N/A")
                .amount(entity.getAmount())
                .date(entity.getDate())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
