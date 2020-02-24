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
            errors.rejectValue("firstName", "Imie powinno składać się tylko z liter i mieć minimum 3 znaki!");
        }

        if(Objects.isNull(user.getLastName()) || !user.getLastName().matches("[A-Za-ząćęłńóśźżĄĆĘŁŃŚŻŹ]{3,}")){
            errors.rejectValue("lastName", "Nazwisko powinno składać się tylko z liter i mieć minimum 3 znaki!");
        }

        if(Objects.isNull(user.getUsername()) || user.getUsername().length() < 3){
            errors.rejectValue("username", "Nazwa użytkownika powinna składać się z minimum 3 znaków!");
        }

        if(Objects.isNull(user.getEmail()) || !emailValidator.isValid(user.getEmail())){
            errors.rejectValue("email", "Email jest niepoprawny!");
        }

        if(Objects.isNull(user.getPassword()) || user.getPassword().length() < 3){
            errors.rejectValue("password", "Hasło powinno składać się z minimum 3 znaków!");
        }

        if(Objects.isNull(user.getRepeatedPassword()) || !user.getPassword().equals(user.getRepeatedPassword())){
            errors.rejectValue("repeatedPassword", "Podane hasła nie zgadzają się lub pole jest puste!");
        }

    }
}
