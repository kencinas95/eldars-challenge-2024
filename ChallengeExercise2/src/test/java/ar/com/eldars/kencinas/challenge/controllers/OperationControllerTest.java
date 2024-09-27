package ar.com.eldars.kencinas.challenge.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class OperationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldGetOperationRatesFailureBadRequest() throws Exception {
        mockMvc.perform(
                        get("/api/v1/operation/rate")
                                .queryParam("brand", "MASTERCARD")
                                .queryParam("amount", "10.0"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}
