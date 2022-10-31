package com.project_4.cookpad_api.repository;

import com.project_4.cookpad_api.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {
}
