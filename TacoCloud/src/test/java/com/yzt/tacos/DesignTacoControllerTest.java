package com.yzt.tacos;

import com.yzt.tacos.data.IngredientRepository;
import com.yzt.tacos.data.OrderRepository;
import com.yzt.tacos.data.TacoRepository;
import com.yzt.tacos.web.DesignTacoController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(DesignTacoController.class)
public class DesignTacoControllerTest {

  @Autowired
  private MockMvc mockMvc;

  private List<Ingredient> ingredients;

  private Taco design;

  @MockBean
  private IngredientRepository ingredientRepository;

  @MockBean
  private TacoRepository designRepository;

  @MockBean
  private OrderRepository orderRepository;

  @Before
  public void setup() {
    ingredients = Arrays.asList(
      new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP),
      new Ingredient("COTO", "Corn Tortilla", Ingredient.Type.WRAP),
      new Ingredient("GRBF", "Ground Beef", Ingredient.Type.PROTEIN),
      new Ingredient("CARN", "Carnitas", Ingredient.Type.PROTEIN),
      new Ingredient("TMTO", "Diced Tomatoes", Ingredient.Type.VEGGIES),
      new Ingredient("LETC", "Lettuce", Ingredient.Type.VEGGIES),
      new Ingredient("CHED", "Cheddar", Ingredient.Type.CHEESE),
      new Ingredient("JACK", "Monterrey Jack", Ingredient.Type.CHEESE),
      new Ingredient("SLSA", "Salsa", Ingredient.Type.SAUCE),
      new Ingredient("SRCR", "Sour Cream", Ingredient.Type.SAUCE)
    );

    when(ingredientRepository.findAll())
        .thenReturn(ingredients);

    when(ingredientRepository.findById("FLTO")).thenReturn(Optional.of(new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP)));
    when(ingredientRepository.findById("GRBF")).thenReturn(Optional.of(new Ingredient("GRBF", "Ground Beef", Ingredient.Type.PROTEIN)));
    when(ingredientRepository.findById("CHED")).thenReturn(Optional.of(new Ingredient("CHED", "Cheddar", Ingredient.Type.CHEESE)));

    design = new Taco();
    design.setName("Test Taco");

    design.setIngredients(Arrays.asList(
        new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP),
        new Ingredient("GRBF", "Ground Beef", Ingredient.Type.PROTEIN),
        new Ingredient("CHED", "Cheddar", Ingredient.Type.CHEESE)
  ));

  }

  @Test
  public void testShowDesignForm() throws Exception {
    mockMvc.perform(get("/design"))
        .andExpect(status().isOk())
        .andExpect(view().name("design"))
        .andExpect(model().attribute("wrap", ingredients.subList(0, 2)))
        .andExpect(model().attribute("protein", ingredients.subList(2, 4)))
        .andExpect(model().attribute("veggies", ingredients.subList(4, 6)))
        .andExpect(model().attribute("cheese", ingredients.subList(6, 8)))
        .andExpect(model().attribute("sauce", ingredients.subList(8, 10)));
  }

  @Test
  public void processDesign() throws Exception {
    when(designRepository.save(design))
        .thenReturn(design);

    mockMvc.perform(post("/design")
        .content("name=Test+Taco&ingredients=FLTO,GRBF,CHED")
        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
        .andExpect(status().is3xxRedirection())
        .andExpect(header().stringValues("Location", "/orders/current"));
  }

}
