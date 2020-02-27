package com.example.scrum.validators;

import com.example.scrum.dto.UserPasswordRecoverDto;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Objects;

@Component
public class PasswordRecoveryFormValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return UserPasswordRecoverDto.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {

        UserPasswordRecoverDto userPasswordRecover = (UserPasswordRecoverDto)o;
        EmailValidator emailValidator = EmailValidator.getInstance();

        if(Objects.isNull(userPasswordRecover.getEmail()) || !emailValidator.isValid(userPasswordRecover.getEmail())){
            errors.rejectValue("email", "Podany email jest niepoprawny!");
        }

    }
}
