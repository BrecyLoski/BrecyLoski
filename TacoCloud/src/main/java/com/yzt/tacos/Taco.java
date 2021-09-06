package com.yzt.tacos;

import java.util.List;

import lombok.Data;

@Data
public class Taco {

    private String name;
    private List<String> ingredient;

    public String getName() {
        return name;
    }

    public List<String> getIngredient() {
        return ingredient;
    }
}
