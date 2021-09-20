package com.yzt.tacos;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;

// lombok 并不是 Spring 库
// lombok 提供 @Data

@Data
@RequiredArgsConstructor
/* @RequiredArgsController注解:
* @Data注解会添加一个有参构造器,
* 但使用@NoArgsController后会移除这个构造器,
* 显示添加这个注解以确保除了private的无参构造器外,还有一个有参构造器
* */
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
/* @NoArgsController注解:
* JPA实体需要一个无参构造器,
* access属性设置为AccessLevel.PRIVATE, 使其变成私有的;
* force = true, Lombok生成的构造器会将属性设置为null (这里有必须要设置的final属性);
* */
@Entity// 将Ingredient声明为JPA实体
public class Ingredient {

    @Id // @Id注解: 将其指定为数据库中唯一标识该实体的属性
    private final String id;

    private final String name;
    private final Type type;

    public static enum Type {
        WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public Type getType() {
        return type;
    }
}
