package com.yzt.tacos;

import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
// import org.hibernate.validator.constraints.NotBlank 下的 @NotBlank 已弃用, 注意区分
import javax.validation.constraints.Pattern;

@Data
public class Order {

    @NotBlank(groups = Order.class, message = "Name is required")
    // 不使用@NotNull, 因为" "即不为空, @NotBlank表示不能为 空白字段
    private String name;

    @NotBlank(groups = Order.class, message = "Street is required")
    private String street;

    @NotBlank(groups = Order.class, message = "City is required")
    private String city;

    @NotBlank(groups = Order.class, message = "State is required")
    private String state;

    @NotBlank(groups = Order.class, message = "Zip is required")
    private String zip;

    @CreditCardNumber(groups = Order.class, message = "Not a valid credit card number")
    private String ccNumber;

    @Pattern(groups = Order.class, message = "Must be formatted MM/YY",
    regexp = "^([1-9]|1[0-2])([\\/])([1-9]|[0-9])$")
    // "Pattern - 样式", 使用正则表达式, 确保其符合预期的格式
    private String ccExpiration;

    @Digits(groups = Order.class, integer = 3, fraction = 0, message = "Invalid CVV")
    // "Digit - 数字/位", 确保包含3个整数, 并且没有分数
    private String ccCVV;
}
