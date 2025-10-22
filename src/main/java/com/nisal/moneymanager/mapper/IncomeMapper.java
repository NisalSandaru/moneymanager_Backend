package com.nisal.moneymanager.mapper;

import com.nisal.moneymanager.dto.IncomeDTO;
import com.nisal.moneymanager.entity.CategoryEntiry;
import com.nisal.moneymanager.entity.IncomeEntity;
import com.nisal.moneymanager.entity.ProfileEntity;

public class IncomeMapper {
    public static IncomeEntity toEntity(IncomeDTO dto, ProfileEntity profile, CategoryEntiry category) {
        return IncomeEntity.builder()
                .name(dto.getName())
                .icon(dto.getIcon())
                .amount(dto.getAmount())
                .date(dto.getDate())
                .profile(profile)
                .category(category)
                .build();
    }

    public static IncomeDTO toDto(IncomeEntity entity) {
        return IncomeDTO.builder()
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
