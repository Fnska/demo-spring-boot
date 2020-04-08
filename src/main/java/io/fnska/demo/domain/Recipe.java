package io.fnska.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "recipes")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String description;

    @OneToMany(targetEntity = Ingredient.class, mappedBy = "recipe", cascade = CascadeType.ALL)
    private List<Ingredient> ingredients;
}
