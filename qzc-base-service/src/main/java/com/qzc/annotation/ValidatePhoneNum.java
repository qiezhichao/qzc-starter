package com.qzc.annotation;

import com.qzc.annotation.valiadator.PhoneNumValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.METHOD,
        ElementType.FIELD,
        ElementType.ANNOTATION_TYPE,
        ElementType.CONSTRUCTOR,
        ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {PhoneNumValidator.class})
public @interface ValidatePhoneNum {

    // 是否必填
    boolean requried() default true;

    // 提示信息
    String message() default "手机号码格式错误";

    // 正则表达式模板
    String pattern() default "^[1][3,4,5,7,8][0-9]{9}$";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
