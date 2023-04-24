package api.crud.maven;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@FixMethodOrder(MethodSorters.JVM) // Deixa os métodos de teste na ordem retornada pela JVM.
public class ApiTest {
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
                .body("id", is(1698199113))
                .body("name", is("Sara"))
                .body("category.name", is("dog2"))
                .body("tags.id", hasItem(2022))
                .body("status", is("available"));
        System.out.println("first");
    }
    @Test
    public void consultPet() {
        String petId = "1698199113";

        given()
                .contentType("application/json")
                .log().all()
        .when()
                .get(uri + "/" + petId)
        .then()
                .log().all()
                .statusCode(200)
                .time(lessThan(2000L));
        System.out.println("second");
    }
    @Test
    public void alterPet() throws IOException {
        String jsonBody = readJson("data/register3.json");

        given()
                .contentType("application/json")
                .log().all()
                .body(jsonBody)
        .when()
                .put(uri)
        .then()
                .log().all()
                .statusCode(200)
                .time(lessThan(2000L))
                .body("status", is("sold"));
        System.out.println("third");
    }
    @Test
    public void deletePet() throws IOException {
        String petId = "1698199113";

        given()
                .contentType("application/json")
                .log().all()
        .when()
                .delete(uri + "/" + petId)
        .then()
                .log().all()
                .statusCode(200)
                .time(lessThan(2000L))
                .body("code", is(200))
                .body("type", is("unknown"))
                .body("message", is(petId));
        System.out.println("room");
    }
    @Test
    public void confirmadelitePet() {
        String petId = "1698199113";

        given()
                .contentType("application/json")
                .log().all()
        .when()
                .get(uri + "/" + petId)
        .then()
                .log().all()
                .statusCode(404)
                .time(lessThan(2000L));
        System.out.println("fifth");
    }
}
