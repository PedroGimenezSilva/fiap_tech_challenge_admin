package br.com.challenge.admin.admin.bdd;

import br.com.challenge.admin.admin.domain.Produto;
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

public class StepDefinitionProduto {
    ObjectMapper mapper = new ObjectMapper()
            .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES) // Ignore unrecognized properties
            .registerModule(new JavaTimeModule());

    private final String ENDPOINT_API_PRODUTO = "https://93kgusvp91.execute-api.us-east-1.amazonaws.com/api/admin/produtos";
    private Produto produto;
    private Response response;

    @Quando("crio um produto")
    public void crio_um_produto() throws JsonProcessingException {
        String requestBody = """
                    {
                        "id": "1",
                        "nome": "mclanche",
                        "preco": 13.0
                    }
                """;

        // Make the POST request
        response = given()
                .header("Content-Type", "application/json") // Set Content-Type header
                .body(requestBody) // Set the body of the request
                .when()
                .post(ENDPOINT_API_PRODUTO) // Endpoint to hit
        ;
    }

    @Então("vejo que o produto foi criado")
    public void vejo_que_o_produto_foi_criado() {
        response.then()
                .statusCode(200) // Validate the response status code (change as per your API)
                .extract()
                .response(); // Extract the response
    }
}
