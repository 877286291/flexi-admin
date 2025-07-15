package top.houyuji.common.base.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import top.houyuji.common.base.validator.ValidNumberValidator;

import java.lang.annotation.*;

/**
 * 可选int值范围校验注解
 */
@Documented
@Constraint(validatedBy = ValidNumberValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidNumber {
    // 默认错误提示信息
    String message() default "必须是指定的int值之一";

    // 可选的int值数组
    int[] values();

    // 分组
    Class<?>[] groups() default {};

    // 负载
    Class<? extends Payload>[] payload() default {};
}
