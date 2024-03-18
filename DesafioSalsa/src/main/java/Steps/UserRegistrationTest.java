package Steps;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class UserRegistrationTest {
	
	@BeforeAll
	public static void setup() {
		RestAssured.baseURI = "https://reqres.in";
		RestAssured.basePath = "/api";
	}
	
	@Test
	public void registerSuccessful() { // Deve registrar Usuário com sucesso
		
		Map<String, String> login	= new HashMap<String, String>();
		login.put("email", "eve.holt@reqres.in");
		login.put("password", "pistol");		
		
		given()
		.log().all()
		.body(login)
		.contentType(ContentType.JSON)
		.when()
			.post("/register")
		.then()
			.log().all()
			.statusCode(200)
			.body("id", is(4))
			.body("token", is("QpwL5tke4Pnpja7X4"))
			;	
			
		}
	
	@Test
	public void registerUnsuccessful() {  // Deve falhar em registrar Usuário
		
		Map<String, String> login	= new HashMap<String, String>();
		login.put("email", "sydney@fife");
		login.put("password", "");		
		
		given()
		.log().all()
		.body(login)
		.contentType(ContentType.JSON)
		.when()
			.post("/register")
		.then()
			.log().all()
			.statusCode(400)
			.body("error", is("Missing password"))
			;

	}
	}
	
	

