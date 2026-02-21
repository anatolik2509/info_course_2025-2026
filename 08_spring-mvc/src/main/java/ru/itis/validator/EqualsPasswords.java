package ru.itis.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = EqualsPasswordValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface EqualsPasswords {
    String message() default "";

    String password();
    String passwordRepeat();

    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
        EqualsPasswords[] value();
    }


    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}