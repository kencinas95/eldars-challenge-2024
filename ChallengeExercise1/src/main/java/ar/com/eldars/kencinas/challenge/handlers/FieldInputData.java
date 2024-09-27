package ar.com.eldars.kencinas.challenge.handlers;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.BiConsumer;
import java.util.function.Function;

@Getter
@AllArgsConstructor
public class FieldInputData<T> {
    private String field;

    private Function<String, Boolean> validator;

    private BiConsumer<String, T> setter;


    public static <M> FieldInputData<M> of(final String field,
                                           final Function<String, Boolean> validator,
                                           final BiConsumer<String, M> setter) {
        return new FieldInputData<>(field, validator, setter);
    }
}
