package cc.lzhong.scalez.util.annotation;

import org.thymeleaf.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneConstraintValidator implements ConstraintValidator<Phone, String> {

    private boolean required = false;

    @Override
    public void initialize(Phone constraintAnnotation) {
        required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (required) {
            return PhoneUtil.isValidPhoneNumber(value);
        } else {
            if (StringUtils.isEmpty(value)) {
                return true;
            } else {
                return PhoneUtil.isValidPhoneNumber(value);
            }
        }
    }
}
