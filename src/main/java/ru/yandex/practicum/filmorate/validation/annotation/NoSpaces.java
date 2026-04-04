package ru.yandex.practicum.filmorate.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.yandex.practicum.filmorate.validation.validator.NoSpacesValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NoSpacesValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface NoSpaces {
    String message() default "Поле не должно содержать пробелов";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}