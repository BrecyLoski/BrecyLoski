package com.yzt.tacos;

import java.util.Date;
import java.util.List;
import javax.validation.constraints.NotNull;
// Java的Bean校验API (Bean Validation API),也被称为JSR-303
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class Taco {

    @NotNull // 不为空,且最少包含5个字符
    @Size(min = 5, message = "Name must be at least 5 characters long")
    private String name;

    @NotNull
    @Size(min = 1, message = "You must choose at least 1 ingredient")
    private List<String> ingredients;

    private Long id;

    private Date CreatedAt;

}
