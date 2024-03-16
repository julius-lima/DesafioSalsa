import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import io.restassured.RestAssured;

public class DelayTest {
	
	@BeforeAll
	public static void setup() {
		RestAssured.baseURI = "https://reqres.in";
		RestAssured.basePath = "/api";
		
		
	}
	
	@Test
	public void listUsersDelay() throws InterruptedException {
{        
	        given()
			.log().all()
		.when()
			.get("/users?delay=3")
		
		.then()
			.log().all()
			
			.statusCode(200)
			.body("data", hasSize(6))
			.body("data.id", hasItems(1, 2, 3, 4, 5, 6))
			.body("data.email", is(not(nullValue())))
			.body("data.first_name", hasItems("George","Janet","Emma","Eve","Charles","Tracey"))
			.body("data.last_name", hasItems("Bluth", "Weaver", "Wong", "Holt", "Morris", "Ramos"))	
			.body("data.avatar", is(not(nullValue())))
            .time(lessThan(5000L)); // Verificar se o tempo de resposta é menor que 5000 milissegundos (5 segundos)
		}

	}
}