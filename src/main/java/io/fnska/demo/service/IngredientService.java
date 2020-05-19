package io.fnska.demo.service;

import io.fnska.demo.domain.Ingredient;
import io.fnska.demo.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientService {


    private IngredientRepository ingredientRepository;

    @Autowired
    public IngredientService(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    public List<Ingredient> findByRecipeId(Long recipeId) {
        return ingredientRepository.findByRecipeId(recipeId);
    }

    public List<Ingredient> saveAllIngredients(List<Ingredient> ingredients) {
        return ingredientRepository.saveAll(ingredients);
    }
}
