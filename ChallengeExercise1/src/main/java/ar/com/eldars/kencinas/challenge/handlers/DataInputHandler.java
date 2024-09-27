package ar.com.eldars.kencinas.challenge.handlers;

import ar.com.eldars.kencinas.challenge.exceptions.FieldInputConvertionException;
import ar.com.eldars.kencinas.challenge.exceptions.FieldInputValidationException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Function;

@Component
public class DataInputHandler<T> {
    public void handle(final List<FieldInputData<T>> fields, final T target) {
        final Scanner in = new Scanner(System.in);

        for (final FieldInputData<T> field : fields) {
            while (true) {
                try {
                    System.out.print("Ingrese " + field.getField() + ":> ");
                    final String input = in.nextLine();

                    if (!field.getValidator().apply(input)) {
                        throw new FieldInputValidationException();
                    }

                    field.getSetter().accept(input, target);
                    break;
                } catch (Exception ex) {
                    System.out.println("Hubo un error, intente nuevamente!");
                }
            }
        }
    }

    public static String next(String message) {
        final Scanner in = new Scanner(System.in);
        while (true) {
            try {
                System.out.print(message);
                String input = in.nextLine();
                if (StringUtils.isBlank(input)) {
                    throw new RuntimeException("blank input");
                }
                return input;
            } catch (RuntimeException ex) {
                System.out.println("Hubo un error y no se pudo leer lo ingresado, intente nuevamente.");
            }
        }
    }

    public static Optional<String> nullableNext(String message) {
        final Scanner in = new Scanner(System.in);
        while (true) {
            try {
                System.out.print(message);
                String input = in.nextLine();
                if (StringUtils.isBlank(input)) {
                    return Optional.empty();
                }
                return Optional.of(input);
            } catch (RuntimeException ex) {
                System.out.println("Hubo un error y no se pudo leer lo ingresado, intente nuevamente.");
            }
        }
    }

    public static <T> T next(String message, Function<String, Boolean> validator, Function<String, T> converter)
            throws FieldInputValidationException, FieldInputConvertionException {
        String value = next(message);

        if (!validator.apply(value)) {
            throw new FieldInputValidationException();
        }
        try {
            return converter.apply(value);
        } catch (Exception ex) {
            throw new FieldInputConvertionException();
        }
    }
}
