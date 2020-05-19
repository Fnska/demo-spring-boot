package io.fnska.demo.controller;

import io.fnska.demo.domain.Ingredient;
import io.fnska.demo.service.IngredientService;
import io.fnska.demo.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/recipes/{recipeId}")
public class IngredientController {

    private IngredientService ingredientService;
    private RecipeService recipeService;

    @Autowired
    public IngredientController(IngredientService ingredientService, RecipeService recipeService) {
        this.ingredientService = ingredientService;
        this.recipeService = recipeService;
    }

    @GetMapping("/ingredients")
    public ResponseEntity<List<Ingredient>> getIngredientsByRecipeId(@PathVariable("recipeId") Long recipeId) {
        return ResponseEntity.ok().body(ingredientService.findByRecipeId(recipeId));
    }
}
