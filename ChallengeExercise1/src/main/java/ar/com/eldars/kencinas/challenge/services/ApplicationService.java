package ar.com.eldars.kencinas.challenge.services;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@Slf4j
@Component
@RequiredArgsConstructor
public class ApplicationService {
    private static final String MENU_MESSAGE = "Bienvenido, por favor elija una de las sigueintes opciones:\n" +
            "1 - Registrar nuevo usuario\n" +
            "2 - Registrar nueva tarjeta\n" +
            "3 - Reporte de tarjetas de usuario\n" +
            "4 - Consultar tasas de marcas\n" +
            "0 - Salir\n";

    private static final String GOODBYE_MESSGE = "Gracias, saliendo...";

    private final Scanner input = new Scanner(System.in);

    private final CustomerService customerService;
    private final CardService cardService;
    private final CardBrandService cardBrandService;

    private Map<Integer, Runnable> options;

    @PostConstruct
    public void postConstruct() {
        options = new HashMap<>();
        options.put(1, customerService::create);
        options.put(2, cardService::create);
        options.put(3, cardService::report);
        options.put(4, cardBrandService::showRates);
    }

    public void mainloop() {
        while (true) {
            try {
                System.out.println(MENU_MESSAGE);
                System.out.print(">:");

                int option = input.nextInt();
                if (option == 0) {
                    System.out.println(GOODBYE_MESSGE);
                    break;
                }

                if (option < 0 || option > 4) {
                    System.out.println("La opcion ingresada no es valida!");
                    continue;
                }

                // Executes
                options.get(option).run();

            } catch (RuntimeException ex) {
                log.error("????", ex);
                System.out.println("La opcion ingresada no pudo ser procesada, por favor, intente nuevamente...");
            } finally {
                System.out.print("Pulse <enter> para continuar...");
                input.nextLine();
                input.nextLine();
                System.out.println("=============================================================================");
            }
        }
    }

}
