package io.fnska.demo.controller;

import io.fnska.demo.domain.Recipe;
import io.fnska.demo.exception.ResourceNotFoundException;
import io.fnska.demo.service.IngredientService;
import io.fnska.demo.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class RecipeController {

    private RecipeService recipeService;
    private IngredientService ingredientService;

    @Autowired
    public RecipeController(RecipeService recipeService, IngredientService ingredientService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
    }

    @GetMapping("/recipes")
    public ResponseEntity<List<Recipe>> getAllRecipes() {
        return ResponseEntity.ok().body(recipeService.findAllRecipes());
    }

    @GetMapping("/recipes/{recipeId}")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable("recipeId") Long recipeId)
            throws ResourceNotFoundException {
        Recipe recipe = recipeService.findRecipeById(recipeId)
                .orElseThrow(() -> new ResourceNotFoundException("Recipe not found :: " + recipeId));
        return ResponseEntity.ok().body(recipe);
    }

    @PostMapping("/recipes")
    public ResponseEntity<Recipe> addRecipe(@Valid @RequestBody Recipe recipeDetails) {
        Recipe recipeToSave = new Recipe();
        recipeToSave.setTitle(recipeDetails.getTitle());
        recipeToSave.setDescription(recipeDetails.getTitle());
        recipeService.saveRecipe(recipeToSave);

        recipeDetails.getIngredients().forEach(ingredient -> ingredient.setRecipe(recipeToSave));
        ingredientService.saveAllIngredients(recipeDetails.getIngredients());
        return ResponseEntity.ok().body(recipeDetails);
    }

    @PutMapping("/recipes/{recipeId}")
    public ResponseEntity<Recipe> updateRecipe(@PathVariable("recipeId") Long recipeId,
                                               @Valid @RequestBody Recipe recipeDetails) throws ResourceNotFoundException {
        return recipeService.findRecipeById(recipeId)
                .map(recipeToSave -> {
                    recipeToSave.setTitle(recipeDetails.getTitle());
                    recipeToSave.setDescription(recipeDetails.getTitle());

                    recipeService.saveRecipe(recipeToSave);
                    recipeDetails.getIngredients().forEach(ingredient -> ingredient.setRecipe(recipeToSave));
                    ingredientService.saveAllIngredients(recipeDetails.getIngredients());
                    return ResponseEntity.ok().body(recipeDetails);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Recipe not found :: " + recipeId));
    }

    @DeleteMapping("/recipes/{recipeId}")
    public Map<String, Boolean> deleteRicipeById(@PathVariable("recipeId") Long recipeId) throws ResourceNotFoundException {
        return recipeService.findRecipeById(recipeId)
                .map(recipe -> {
                    recipeService.delete(recipe);
                    Map<String, Boolean> response = new HashMap<>();
                    response.put("deleted", Boolean.TRUE);
                    return response;
                })
                .orElseThrow(() -> new ResourceNotFoundException("Recipe not found :: " + recipeId));
    }
}
