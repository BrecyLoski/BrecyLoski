package com.yzt.tacos;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.NotBlank;
import lombok.Data;

@Data
public class Order {

    @NotBlank(message = "Name is required")
    // 不使用@NotNull, 因为" "即不为空, @NotBlank表示不能为 空白字段
    private String name;

    @NotBlank(message = "Street is required")
    private String street;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "State is required")
    private String state;

    @NotBlank(message = "Zip is required")
    private String zip;

    @CreditCardNumber(message = "Not a valid credit card number")
    private String ccNumber;

    @Pattern(message = "Must be formatted MM/YY",
    regexp = "^([1-9]|1[0-2])([\\/])([1-9]|[0-9])$")
    // "Pattern - 样式", 使用正则表达式, 确保其符合预期的格式
    private String ccExpiration;

    @Digits(integer = 3, fraction = 0, message = "Invalid CVV")
    // "Digit - 数字/位", 确保包含3个整数, 并且没有分数
    private String ccCVV;
}
