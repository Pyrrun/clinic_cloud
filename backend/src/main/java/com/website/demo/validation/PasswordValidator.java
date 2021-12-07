package com.website.demo.validation;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.function.Predicate;
@Service
public class PasswordValidator implements Predicate<String> {

    public static boolean isValid(String password) {
        if (!password.matches("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$")) {
            throw new IllegalStateException("Password is invalid.");
        }
        return true;

    }

    public static boolean areEqual(String password1, String password2) {
        if (!password1.equals(password2)) {
            throw new IllegalStateException("Passwords are different.");
        }
        return true;
    }


    @Override
    public boolean test(String password) {
        if (!password.matches("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$")) {
            throw new IllegalStateException("Password is invalid.");
        }
        return true;
    }


}
