package com.nisal.moneymanager.mapper;

import com.nisal.moneymanager.dto.CategoryDTO;
import com.nisal.moneymanager.dto.ProfileDTO;
import com.nisal.moneymanager.entity.CategoryEntiry;
import com.nisal.moneymanager.entity.ProfileEntity;

public class CategoryMapper {
    public static CategoryEntiry toEntity(CategoryDTO categoryDTO, ProfileEntity profile) {
        return CategoryEntiry.builder()
                .name(categoryDTO.getName())
                .icon(categoryDTO.getIcon())
                .profile(profile)
                .type(categoryDTO.getType())
                .build();
    }

    public static CategoryDTO toDTO(CategoryEntiry entiry) {
        return CategoryDTO.builder()
                .id(entiry.getId())
                .profileId(entiry.getProfile() !=null ? entiry.getProfile().getId(): null)
                .name(entiry.getName())
                .icon(entiry.getIcon())
                .createdAt(entiry.getCreatedAt())
                .updatedAt(entiry.getUpdatedAt())
                .type(entiry.getType())
                .build();
    }
}
