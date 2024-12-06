package br.com.challenge.admin.admin.bdd;

import br.com.challenge.admin.admin.domain.Cliente;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class StepDefinitionCliente {
    ObjectMapper mapper = new ObjectMapper()
            .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES) // Ignore unrecognized properties
            .registerModule(new JavaTimeModule());

    private final String ENDPOINT_API_CLIENTE = "http://localhost:8082/clientes";
    private Cliente cliente;

    @Quando("crio um cliente")
    public void crio_um_cliente() throws JsonProcessingException {
        String requestBody = """
                    {
                        "id": "1",
                        "nome": "Joao",
                        "email": "joao@mail.com"
                    }
                """;

        // Make the POST request
        Response response = given()
                .header("Content-Type", "application/json") // Set Content-Type header
                .body(requestBody) // Set the body of the request
                .when()
                .post(ENDPOINT_API_CLIENTE) // Endpoint to hit
                .then()
                .statusCode(201) // Validate the response status code (change as per your API)
                .extract()
                .response(); // Extract the response

        cliente = mapper.readValue(response.asString(), Cliente.class);
    }

    @Então("vejo que o cliente foi criado")
    public void vejo_que_o_cliente_foi_criado() {
        assertNotNull(cliente);
        assertNotEquals(0, cliente.getId());
        assertNotNull(cliente.getNome());
        assertNotNull(cliente.getEmail());
    }
}
