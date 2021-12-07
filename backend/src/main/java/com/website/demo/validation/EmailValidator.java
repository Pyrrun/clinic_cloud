package com.website.demo.validation;

import org.springframework.stereotype.Service;

import java.util.function.Predicate;

@Service
public class EmailValidator implements Predicate<String> {

    public static boolean isValid(String email) {
        if (!email.matches("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?")) {
            throw new IllegalStateException("E-mail is invalid.");
        }
        return true;
    }

    public static boolean areEqual(String email1, String email2) {
        if(!email1.equals(email2)){
            throw new IllegalStateException("E-mails are different.");
        }
        return true;
    }

    @Override
    public boolean test(String email) {
        if (!email.matches("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?")) {
            throw new IllegalStateException("E-mail is invalid.");
        }
        return true;
    }
}
