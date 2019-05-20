package com.qzc.annotation.valiadator;

import com.qzc.annotation.ValidatePhoneNum;
import com.qzc.util.BasePatternUtil;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 手机号码校验器
 *
 * @Author: qiezhichao
 * @CreateDate: 2019/5/13 20:47
 */
public class PhoneNumValidator implements ConstraintValidator<ValidatePhoneNum, String> {
    /* ConstraintValidator<IsMobile, String>
     * 第一个参数是自定义注解的名字 第二个是注解修饰的字段类型
     */

    private boolean required = false;

    private String pattern = "";

    @Override
    public void initialize(ValidatePhoneNum validatePhoneNum) {
        required = validatePhoneNum.requried();
        pattern = validatePhoneNum.pattern();
    }

    @Override
    public boolean isValid(String phoneNum, ConstraintValidatorContext constraintValidatorContext) {
        if (required) {
            return BasePatternUtil.isMatch(pattern, phoneNum);
        } else {
            if (StringUtils.isEmpty(phoneNum)) {
                return true;
            }

            return BasePatternUtil.isMatch(pattern, phoneNum);
        }
    }
}
