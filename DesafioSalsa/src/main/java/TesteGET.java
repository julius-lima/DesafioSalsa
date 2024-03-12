import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.Test;


public class TesteGET {
	
	@Test
	public void listUsers() {
		given()
			.log().all()
		.when()
			.get("https://reqres.in/api/userswpd	")
		.then()
			.log().all()
			.statusCode(200)
			.body("data", hasSize(6))
			.body("data.avatar", is(not(nullValue())))
			;
	}
	@Test
	public void listUsersPage2() {		
		given()
			.log().all()
		.when()
			.get("https://reqres.in/api/users?page=2")
		.then()
			.log().all()
			.statusCode(200)
			.body("data", hasSize(6))
			.body("data.avatar", is(not(nullValue())))
			;		
	}
			
	@Test
	public void singleUser() {		
		given()
		///	.log().all()
		.when()
			.get("https://reqres.in/api/users/2")
		.then()
			//.log().all()
			.statusCode(200)
			.body("data.id", is(2))
			.body("data.email", is("janet.weaver@reqres.in"))
			.body("data.avatar", is(not(nullValue())))
			;		
	}
		
	@Test
	public void userNotFound() {		
		given()
			.log().all()
		.when()
			.get("https://reqres.in/api/users/23")
		.then()
			.log().all()
			.statusCode(404)
			;		
	}
		
		
	@Test
	public void listResources() {		
		given()
			.log().all()
		.when()
			.get("https://reqres.in/api/unknown")
		.then()
			.log().all()
			.statusCode(200)
			.body("data", hasSize(6))
			.body("data.pantone_value", is(not(nullValue())))
			;		
	}
	
	@Test
	public void listResourcesPage2() {		
		given()
			.log().all()
		.when()
			.get("https://reqres.in/api/unknown?page=2")
		.then()
			.log().all()
			.statusCode(200)
			.body("data", hasSize(6))
			.body("data.pantone_value", is(not(nullValue())))
			;		
	}
		
	@Test
	public void singleResource() {		
		given()
			.log().all()
		.when()
			.get("https://reqres.in/api/unknown/2")
		.then()
			.log().all()
			.statusCode(200)
			.body("data.id", is(2))
			.body("data.pantone_value", is(not(nullValue())))
			;		
	}
		
	@Test
	public void resourceNotFound() {		
		given()
			.log().all()
		.when()
			.get("https://reqres.in/api/unknown/23")
		.then()
			.log().all()
			.statusCode(404)
			;		
	}

}
