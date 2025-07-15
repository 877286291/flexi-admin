package top.houyuji.common.base.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import top.houyuji.common.base.annotation.ValidNumber;

import java.util.Arrays;

public class ValidNumberValidator implements ConstraintValidator<ValidNumber, Integer> {
    private int[] allowedValues;

    @Override
    public void initialize(ValidNumber constraintAnnotation) {
        this.allowedValues = constraintAnnotation.values();
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        // 如果值为null，由 @NotNull 校验
        if (value == null) {
            return true;
        }
        // 判断值是否在可选的int值数组中
        return Arrays.stream(allowedValues).anyMatch(val -> val == value);
    }
}
