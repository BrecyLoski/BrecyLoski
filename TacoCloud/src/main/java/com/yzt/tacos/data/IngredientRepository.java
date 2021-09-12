package com.yzt.tacos.data;

import com.yzt.tacos.Ingredient;

public interface IngredientRepository {

    Iterable<Ingredient> findAll(); //查询所有配料信息

    Ingredient findOne(String id);//根据id, 查询单个Ingredient

    Ingredient save(Ingredient ingredient); //保存Ingredient对象
}
