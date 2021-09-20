package com.yzt.tacos.data;

import org.springframework.data.repository.CrudRepository;
import com.yzt.tacos.Ingredient;

/* CrudRepository<Ingredient, String>:
* 第一个参数是要持久化的实体类型,
* 第二个参数是实体ID属性的类型.
* */
public interface IngredientRepository extends CrudRepository<Ingredient, String> {

}
