package com.example.demo.validator;

import com.example.demo.models.UserModel;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class UserValidator implements Validator {

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return UserModel.class.equals(aClass); // TODO PANDA: what does it mean? quack-quack
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserModel user = (UserModel) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");

        if (userService.findByUsername(user.getUsername()) != null)
            errors.rejectValue("username", "Size.userForm.username");

        /*
        if (!user.getPasswordConfirm().equals(user.getPassword()))
            errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm");
         */
    }
}
