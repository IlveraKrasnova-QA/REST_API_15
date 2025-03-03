import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;


public class SimpleTests {

    @Test
    void successfulUsersNameSearchTest () {
        get("https://reqres.in/api/users/2")
                .then()
                .log().all()
                .statusCode(200)
                .body("data.first_name", is("Janet"));
    }

    @Test
    void successfulNewUserTest () {
        String newUser = "{\"name\": \"morpheus\", \"job\": \"leader\"}";
        given()
                .body(newUser)
                .contentType(JSON)
                .log().uri()
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .body("name", is("morpheus"))
                .body("job", is("leader"));
    }

    @Test
    void creatingNullUserTest () {
        String nullUser = "{\"name\": \"\", \"job\": \"\"}";
        given()
                .body(nullUser)
                .contentType(JSON)
                .log().uri()
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .body("name", is(""))
                .body("job", is(""));
    }

    @Test
    void creatingWithoutJobTest() {
        String userWithoutJob = "{\"name\": \"morpheus\", \"job\": \"\"}";
        given()
                .body(userWithoutJob)
                .contentType(JSON)
                .log().uri()
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .body("name", is("morpheus"))
                .body("job", is(""));
    }

    @Test
    void successfulDeletingTest () {
        String user = "{\"first_name\": \"Janet\", \"last_name\": \"Weaver\"}";
        given()
                .body(user)
                .contentType(JSON)
                .log().uri()
                .when()
                .delete("https://reqres.in/api/users/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(204)
                .statusLine("HTTP/1.1 204 No Content");
    }

}