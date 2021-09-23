package yzt.com.tacos;

import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
// Java的Bean校验API (Bean Validation API),也被称为JSR-303
import javax.validation.constraints.Size;
import lombok.Data;

@Data
@Entity// 声明为JPA实体
public class Taco {

    @Id // @Id注解: 将其指定为数据库中唯一标识该实体的属性
    @GeneratedValue(strategy = GenerationType.AUTO) // 依赖数据库自动生成的ID值
    private Long id;

    private Date createdAt;

    @NotNull // 不为空,且最少包含5个字符
    @Size(min = 5, message = "Name must be at least 5 characters long")
    private String name;

    @ManyToMany(targetEntity = Ingredient.class)
    // 声明Taco与其关联的Ingredient列表之间的关系
    @Size(min = 1, message = "You must choose at least 1 ingredient")
    private List<Ingredient> ingredients;

    @PrePersist
    // 在持久化之前, 使用createdAt()设置当前日期和时间
    void createdAt(){
        this.createdAt = new Date();
    }
}
