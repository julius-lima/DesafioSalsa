package Steps;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class UserLoginTest {
	
	@BeforeAll
	public static void setup() {
		RestAssured.baseURI = "https://reqres.in";
		RestAssured.basePath = "/api";
	}
	
	@Test
	public void loginSuccessful() { // Deve logar Usuário com sucesso
		
		Map<String, String> login	= new HashMap<String, String>();
		login.put("email", "eve.holt@reqres.in");
		login.put("password", "cityslicka");		
		
		given()
		.log().all()
		.body(login)
		.contentType(ContentType.JSON)
		.when()
			.post("/login")
		.then()
			.log().all()
			.statusCode(200)			
			.body("token", is("QpwL5tke4Pnpja7X4"))
			;	
			
		}
	
	@Test
	public void loginUnsuccessful() {		// Deve falhar em logar Usuário
		Map<String, String> login	= new HashMap<String, String>();
		login.put("email", "peter@klaven");
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
