import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;


public class TesteGET {
	
	public static RequestSpecification reqSpec;
	public static ResponseSpecification resSpec;
	public static ResponseSpecification resSpec404;
	
	@BeforeAll
	public static void setup() {
		RestAssured.baseURI = "https://reqres.in";
		RestAssured.basePath = "/api";
		
		RequestSpecBuilder reqBuilder = new RequestSpecBuilder();
		reqBuilder.log(LogDetail.ALL);
		reqSpec  = reqBuilder.build();
		
		ResponseSpecBuilder resBuilder = new ResponseSpecBuilder();
		resBuilder.expectStatusCode(200);
		resBuilder.log(LogDetail.ALL);
		resSpec  = resBuilder.build();
		
		ResponseSpecBuilder resBuilder404 = new ResponseSpecBuilder();
		resBuilder404.expectStatusCode(404);
		resBuilder.log(LogDetail.ALL);
		resSpec404  = resBuilder404.build();
	}
	
	@Test
	public void listUsers() {		
		given()
			.spec(reqSpec)
		.when()
			.get("/users")
		.then()
			.spec(resSpec)
			.body("data", hasSize(6))
			.body("data.id", hasItems(1, 2, 3, 4, 5, 6))
			.body("data.email", is(not(nullValue())))
			.body("data.first_name", hasItems("George","Janet","Emma","Eve","Charles","Tracey"))
			.body("data.last_name", hasItems("Bluth", "Weaver", "Wong", "Holt", "Morris", "Ramos"))	
			.body("data.avatar", is(not(nullValue())))
			;
	}
	@Test
	public void listUsersPage2() {		
		given()
			.spec(reqSpec)
		.when()
			.get("users?page=2")
		.then()
			.spec(resSpec)
			.body("data", hasSize(6))
			.body("data.id", hasItems(7, 8, 9 , 10 , 11, 12))
			.body("data.email", is(not(nullValue())))
			.body("data.first_name", hasItems("Michael", "Lindsay", "Tobias" , "Byron", "George", "Rachel"))
			.body("data.last_name", hasItems("Lawson", "Ferguson", "Funke", "Fields", "Edwards", "Howell"))	
			.body("data.avatar", is(not(nullValue())))
			;		
	}
			
	@Test
	public void singleUser() {		
		given()
			.spec(reqSpec)
		.when()
			.get("/users/2")
		.then()
			.spec(resSpec)
			.body("data.id", is(2))
			.body("data.email", is("janet.weaver@reqres.in"))
			.body("data.first_name", is("Janet"))
			.body("data.last_name", is("Weaver"))
			.body("data.avatar", is("https://reqres.in/img/faces/2-image.jpg"))
			;		
	}
		
	@Test
	public void userNotFound() {		
		given()
			.spec(reqSpec)
		.when()
			.get("/users/23")
		.then()
			.spec(resSpec404)
			;		
	}
		
		
	@Test
	public void listResources() {		
		given()
			.spec(reqSpec)
		.when()
			.get("/unknown")
		.then()
			.spec(resSpec)
			.body("data", hasSize(6))
			.body("data.pantone_value", is(not(nullValue())))
			;		
	}
	
	@Test
	public void listResourcesPage2() {		
		given()
			.spec(reqSpec)
		.when()
			.get("/unknown?page=2")
		.then()
			.spec(resSpec)
			.body("data", hasSize(6))
			.body("data.pantone_value", is(not(nullValue())))
			;		
	}
		
	@Test
	public void singleResource() {		
		given()
			.spec(reqSpec)
		.when()
			.get("/unknown/2")
		.then()
			.spec(resSpec)
			.body("data.id", is(2))
			.body("data.pantone_value", is(not(nullValue())))
			;		
	}
		
	@Test
	public void resourceNotFound() {		
		given()
			.spec(reqSpec)
		.when()
			.get("/unknown/23")
		.then()
			.spec(resSpec404)
			;		
	}

}
