package com.example.scrum.validators;

import com.example.scrum.dto.UserRegisterDto;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Objects;

@Component
public class UserValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return UserRegisterDto.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {

        UserRegisterDto user = (UserRegisterDto)o;
        EmailValidator emailValidator = EmailValidator.getInstance();

        if(Objects.isNull(user.getFirstName()) || !user.getFirstName().matches("[A-Za-ząćęłńóśźżĄĆĘŁŃŚŻŹ]{3,}")){
            errors.rejectValue("firstName", "First name should only consist of letters and have a minimum of 3 characters!");
        }

        if(Objects.isNull(user.getLastName()) || !user.getLastName().matches("[A-Za-ząćęłńóśźżĄĆĘŁŃŚŻŹ]{3,}")){
            errors.rejectValue("lastName", "Surname should only consist of letters and have a minimum of 3 characters!");
        }

        if(Objects.isNull(user.getUsername()) || user.getUsername().length() < 3){
            errors.rejectValue("username", "Username should have minimum 3 characters!");
        }

        if(Objects.isNull(user.getEmail()) || !emailValidator.isValid(user.getEmail())){
            errors.rejectValue("email", "Email is not correct!");
        }

        if(Objects.isNull(user.getPassword()) || user.getPassword().length() < 3){
            errors.rejectValue("password", "Password should consist of minimum 3 characters!");
        }

        if(Objects.isNull(user.getRepeatedPassword()) || !user.getPassword().equals(user.getRepeatedPassword())){
            errors.rejectValue("repeatedPassword", "The passwords do not match or the field is empty!");
        }

    }
}
