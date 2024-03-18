package Steps;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.notNullValue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import io.restassured.RestAssured;

public class TestUpdateChangeUsers {	

	@BeforeAll
	public static void setup() {
		RestAssured.baseURI = "https://reqres.in";
		RestAssured.basePath = "/api";
	}
	
	@Test
	public void createUsers() {		//Deve criar usuário com sucesso
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
	public void updateUsers() {		//Deve alterar usuário com sucesso
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
	public void partiallyUpdateUser() { //Deve alterar parcialmente o usuário com sucesso
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
	public void deleteUser() { //Deve deletar o usuário
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
