import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Function;

public class Challenge4 {
    public static class Solution {
        private static double median(final List<Integer> values) {
            values.sort(Comparator.naturalOrder());

            if (values.size() % 2 != 0) {
                return (double) values.get(values.size() / 2);
            } else {
                final int i = Math.floorDiv(values.size(), 2);
                return Math.floorDiv((values.get(i) + values.get(i + 1)), 2);
            }
        }

        public static int resolve(final int frame) {
            System.out.println("Starting the load of the cost amounts:");

            int i = 0;
            int alerts = 0;
            LinkedList<Integer> values = new LinkedList<>();

            while (true) {
                final String prompt = String.format("[Day #%d] Amount: $", i + 1);
                final Integer amount = InputHandler.next(prompt, Integer::parseInt);
                if (amount == 0) {
                    if (values.size() != frame) {
                        System.out.println("Please, keep inserting values, not enough yet!");
                    } else {
                        break;
                    }
                } else {
                    if (values.size() == frame) {
                        final double median = median((List<Integer>) values.clone());
                        if (amount >= (2 * median)) {
                            System.out.printf("[Notification] FRAUD ALERT #%d: $%d\n", alerts + 1, amount);
                            alerts++;
                        }
                        values.pop();
                    }
                    values.addLast(amount);
                    i++;
                }
            }

            return alerts;
        }
    }


    public static void main(String[] args) {
        System.out.println("Starting National Bank fraud alert checker...");

        final Integer frame = InputHandler.next("Time frame (days): ", Integer::parseInt);
        final int alerts = Solution.resolve(frame);
        System.out.println("Count of alerts: " + alerts);

    }

    private static class InputHandler {
        public static String next(String message) {
            final Scanner in = new Scanner(System.in);
            while (true) {
                try {
                    System.out.print(message);
                    return in.nextLine();
                } catch (RuntimeException ex) {
                    System.out.println("An error has occurred and couldn't read the value, please try again...");
                }
            }
        }

        public static Optional<String> nullableNext(String message) {
            final Scanner in = new Scanner(System.in);
            while (true) {
                try {
                    System.out.print(message);
                    String input = in.nextLine();
                    if (input.isBlank()) {
                        return Optional.empty();
                    }
                    return Optional.of(input);
                } catch (RuntimeException ex) {
                    System.out.println("Hubo un error y no se pudo leer lo ingresado, intente nuevamente.");
                }
            }
        }

        public static <T> T next(String message, Function<String, T> converter) {
            String value = next(message);
            try {
                return converter.apply(value);
            } catch (Exception ex) {
                throw new RuntimeException();
            }
        }
    }
}
