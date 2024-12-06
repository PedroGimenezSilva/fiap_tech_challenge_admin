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

public class StepDefinitionCliente {
    ObjectMapper mapper = new ObjectMapper()
            .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES) // Ignore unrecognized properties
            .registerModule(new JavaTimeModule());

    private final String ENDPOINT_API_CLIENTE = "https://93kgusvp91.execute-api.us-east-1.amazonaws.com/api/admin/clientes";
    private Cliente cliente;
    private Response response;

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
        response = given()
                .header("Content-Type", "application/json") // Set Content-Type header
                .body(requestBody) // Set the body of the request
                .when()
                .post(ENDPOINT_API_CLIENTE) // Endpoint to hit
        ;
    }

    @Então("vejo que o cliente foi criado")
    public void vejo_que_o_cliente_foi_criado() {
        response.then()
                .statusCode(200) // Validate the response status code (change as per your API)
                .extract()
                .response(); // Extract the response
    }
}
