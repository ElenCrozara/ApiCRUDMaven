package api.crud.maven;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ApiTest {
    // função de leitura do arquivo que contém o json
    public String readJson(String pathJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(pathJson)));
    }
    String uri = "https://petstore.swagger.io/v2/pet";

    @Test
    public void registerPets() throws IOException, ParseException {
        String jsonBody = readJson("data/register2.json");
        given()
                .contentType("application/json")
                .log().all()
                .body(jsonBody)
        .when()
                .post(uri)
        .then()
                .log().all()
                .statusCode(200)
                .body("name", is("Sara"))
                .body("category.name", is("dog"))
                .body("tags.id", hasItem(2022))
                .body("status", is("available"));
    }
    @Test
    public void consultPet() {
        String petId = "16981";

        given()
                .contentType("application/json")
                .log().all()
        .when()
                .get(uri + "/" + petId)
        .then()
                .log().all()
                .statusCode(200)
                .time(lessThan(2000L));
    }
    @Test
    public void deletePet() throws IOException {


    }
}
