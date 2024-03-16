import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.notNullValue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import io.restassured.RestAssured;

public class TestPost {	

	@BeforeAll
	public static void setup() {
		RestAssured.baseURI = "https://reqres.in";
		RestAssured.basePath = "/api";
	}
	
	@Test
	public void createUsers() {		
		given()
			.log().all()
			.contentType("application/json")
			.body("{\"name\": \"morpheus\",\"job\": \"leader\"}")
		.when()
			.post("/users")
		.then()
			.log().all()
			.statusCode(201)
			.body("id", is(notNullValue()))
			.body("name", is("morpheus"))
			.body("job", is("leader"))
		;
	}
	
	@Test
	public void updateUsers() {		
		given()
			.log().all()
			.contentType("application/json")
			.body("{\"name\": \"morpheus\",\"job\": \"zion resident\"}")
		.when()
			.put("/users/2")
		.then()
			.log().all()
			.statusCode(200)		
			.body("name", is("morpheus"))
			.body("job", is("zion resident"))
		;
	}
	
	@Test
	public void updateParcialUser() {
		given()
			.log().all()
			.contentType("application/json")
			.body("{\"job\": \"QA Analist\"}")
		.when()
			.patch("/users/2")
		.then()
			.log().all()
			.statusCode(200)
		;		
	}
	
	@Test
	public void deleteUser() {
		given()
			.log().all()
		.when()
			.delete("/users/2")
		.then()
			.log().all()
			.statusCode(204)
		;
		
		
	}
	
}
