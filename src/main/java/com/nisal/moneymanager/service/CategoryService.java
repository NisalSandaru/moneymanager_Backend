package com.nisal.moneymanager.service;

import com.nisal.moneymanager.dto.CategoryDTO;
import com.nisal.moneymanager.entity.CategoryEntiry;
import com.nisal.moneymanager.entity.ProfileEntity;
import com.nisal.moneymanager.mapper.CategoryMapper;
import com.nisal.moneymanager.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final ProfileService profileService;
    private final CategoryRepository categoryRepository;

    public CategoryDTO saveCategory(CategoryDTO categoryDTO) {
        ProfileEntity profile = profileService.getCurrentProfile();
        if (categoryRepository.existsByNameAndProfileId(categoryDTO.getName(), profile.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category name already exists");
        }

        CategoryEntiry newCategory = CategoryMapper.toEntity(categoryDTO, profile);
        newCategory = categoryRepository.save(newCategory);
        return CategoryMapper.toDTO(newCategory);
    }

    public List<CategoryDTO> getCategoriesForCurrentUser() {
        ProfileEntity profile = profileService.getCurrentProfile();
        List<CategoryEntiry> categories = categoryRepository.findByProfileId(profile.getId());
        return categories.stream().map(CategoryMapper::toDTO).toList();
    }

    //get CategoriesByTypeForCurrentUser
    public List<CategoryDTO> getCategoriesByTypeForCurrentUser(String type) {
        ProfileEntity profile = profileService.getCurrentProfile();
        List<CategoryEntiry> categories = categoryRepository.findByTypeAndProfileId(type, profile.getId());
        return categories.stream().map(CategoryMapper::toDTO).toList();
    }

    public CategoryDTO updateCategory(Long categoryId, CategoryDTO dto) {
        ProfileEntity profile = profileService.getCurrentProfile();
        CategoryEntiry existingCategory = categoryRepository.findByIdAndProfileId(categoryId, profile.getId())
                .orElseThrow(() -> new RuntimeException("Category not found or not accessible"));
        if (categoryRepository.existsByNameAndProfileId(dto.getName(), profile.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category name already exists");
        }
        existingCategory.setName(dto.getName());
        existingCategory.setIcon(dto.getIcon());
        existingCategory = categoryRepository.save(existingCategory);
        return CategoryMapper.toDTO(existingCategory);
    }

}
