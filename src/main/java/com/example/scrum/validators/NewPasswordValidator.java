package com.example.scrum.validators;

import com.example.scrum.dto.PasswordDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.Objects;

@Component
public class NewPasswordValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return PasswordDto.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {

        PasswordDto passwordDto = (PasswordDto)o;

        if(Objects.isNull(passwordDto.getPassword()) || passwordDto.getPassword().length() < 3){
            errors.rejectValue("password", "Hasło powinno składać się z minimum 3 znaków!");
        }

        if(Objects.isNull(passwordDto.getRepeatedPassword()) || !passwordDto.getPassword().equals(passwordDto.getRepeatedPassword())){
            errors.rejectValue("repeatedPassword", "Podane hasła nie zgadzają się lub pole jest puste!");
        }

    }
}
