package com.example.demo.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.service.UserDetailsServiceImpl;

public class UniqueLoginValidator implements ConstraintValidator<UniqueLogin, String> {

    private final UserDetailsServiceImpl userDetailsService;

    public UniqueLoginValidator() {
        this.userDetailsService = null;
    }

    @Autowired
    public UniqueLoginValidator(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return userDetailsService == null || userDetailsService.findByUsername(value) == null;
    }
}
