package cc.lzhong.scalez.util.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy={PhoneConstraintValidator.class})
public @interface Phone {

    boolean required() default true;

    String message() default "Invalid Phone Number";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
