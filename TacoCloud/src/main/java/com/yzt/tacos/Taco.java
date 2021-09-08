package com.yzt.tacos;

import java.util.List;
import javax.validation.constraints.NotNull;
// Java的Bean校验API (Bean Validation API),也被称为JSR-303
import javax.validation.constraints.Size;

import com.yzt.tacos.web.DesignTacoController;
import lombok.Data;

@Data
public class Taco {

    @NotNull(groups = DesignTacoController.class) // 不为空;
    @Size(groups = DesignTacoController.class, min = 5, message = "Name must be at least 5 characters long")
    private String name;

    @Size(groups = DesignTacoController.class, min = 1, message = "You must choose at least 1 ingredient")
    private List<String> ingredients;

}
