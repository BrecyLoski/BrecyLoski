package com.yzt.tacos;

import com.yzt.tacos.Ingredient;
import org.springframework.stereotype.Component;

@Component
public class Taco {

    private String id;
    private String name;
    private Ingredient ingredient;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }
}
