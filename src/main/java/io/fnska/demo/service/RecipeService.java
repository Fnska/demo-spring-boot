package io.fnska.demo.service;

import io.fnska.demo.domain.Recipe;
import io.fnska.demo.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {

    private RecipeRepository recipeRepository;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public List<Recipe> findAllRecipes() {
        return recipeRepository.findAll();
    }

    public Optional<Recipe> findRecipeById(Long id) {
        return recipeRepository.findById(id);
    }

    public Recipe saveRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    public void deleteRecipeById(Long id) {
        recipeRepository.deleteById(id);
    }

    public void delete(Recipe recipe) {
        recipeRepository.delete(recipe);
    }

}
