package api.crud.maven;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

public class ApiTest {
    // função de leitura do arquivo que contém o json
    public String readJson(String pathJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(pathJson)));
    }
    @Test // notação do Junit que declara uma stepe de teste
    public void registerPerson() throws IOException { // metodo que não retorna nada mas executa algo
        String uri = "https://petstore.swagger.io/v2/pet";
        String jsonBody = readJson("data/register1.json");

        given()
                .contentType("application/json")// tipo da informação
                .log().all() // registrar tudo que eu to fazendo
                .body(jsonBody) //jsonBody é a variável que guarda o caminho do arquivo que deve ser enviado

        .when()
                .post(uri)

        .then()
                .log().all()
                .statusCode(200)
                .body("name", equalTo("Carol"))
                .body("status", equalTo("available"))
                .time(lessThan(2000L))



        ;



    }
}
