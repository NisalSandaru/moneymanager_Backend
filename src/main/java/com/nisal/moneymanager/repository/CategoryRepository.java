package com.nisal.moneymanager.repository;

import com.nisal.moneymanager.entity.CategoryEntiry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryEntiry, Long> {

    List<CategoryEntiry> findByProfileId(Long profileId);

    Optional<CategoryEntiry> findByIdAndProfileId(Long id, Long profileId);

    List<CategoryEntiry> findByTypeAndProfileId(String type, Long profileId);

    Boolean existsByNameAndProfileId(String name, Long profileId);
}
