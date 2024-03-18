package Steps;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;


public class TesteListUsersResources {
	
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
	public void listUsers() {	//Deve listar usuário da pagina 1	
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
			
			.body("support.url", is("https://reqres.in/#support-heading"))
			.body("support.text", is("To keep ReqRes free, contributions towards server costs are appreciated!"))
			;
	}
	@Test
	public void listUsersPage2() {		//Deve listar usuário da pagina 2
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
	public void singleUser() {		//Deve listar usuário 2
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
	public void downloadAvatar() throws IOException {		
		byte[] image = given()
			.spec(reqSpec)
		.when()
			.get("https://reqres.in/img/faces/2-image.jpg")
		.then()
			.spec(resSpec)
			.extract().asByteArray();		
		;
		File imagem = new File("src/main/resources/file.jpg");
		OutputStream out = new FileOutputStream(imagem);
		out.write(image);
		out.close();
		
		/*Atualizar pasta src/main/resources para visualizar o Avatar do Ususário */
			
			
	}
		
	@Test
	public void userNotFound() {		//Deve sinalizar que usuário não foi encontrado
		given()
			.spec(reqSpec)
		.when()
			.get("/users/23")
		.then()
			.spec(resSpec404)
			;		
	}
		
		
	@Test
	public void listResources() {		//Deve listar recursos da pagina 1
		given()
			.spec(reqSpec)
		.when()
			.get("/unknown")
		.then()
			.spec(resSpec)
			.body("data", hasSize(6))
			.body("data.id", hasItems(1, 2, 3, 4, 5, 6))
			.body("data.name", hasItems("cerulean", "fuchsia rose", "true red", "aqua sky", "tigerlily", "blue turquoise"))
			.body("data.year", hasItems(2000, 2001, 2002, 2003, 2004, 2005))
			.body("data.color", hasItems("#98B2D1", "#C74375", "#BF1932" , "#7BC4C4", "#E2583E", "#53B0AE"))				
			.body("data.pantone_value", hasItems("15-4020", "17-2031", "19-1664" , "14-4811", "17-1456", "15-5217"))
			;		
	}
	
	@Test
	public void listResourcesPage2() {		//Deve listar recursos da pagina 2
		given()
			.spec(reqSpec)
		.when()
			.get("/unknown?page=2")
		.then()
			.spec(resSpec)
			.body("data", hasSize(6))
			.body("data.id", hasItems(7, 8, 9, 10, 11, 12))
			.body("data.name", hasItems("sand dollar", "chili pepper", "blue iris", "mimosa", "turquoise", "honeysuckle"))
			.body("data.year", hasItems(2006, 2007, 2008, 2009, 2010, 2011))
			.body("data.color", hasItems("#DECDBE", "#9B1B30", "#5A5B9F" , "#F0C05A", "#45B5AA", "#D94F70"))				
			.body("data.pantone_value", hasItems("13-1106", "19-1557", "18-3943" , "14-0848", "15-5519", "18-2120"))
			;		
	}
		
	@Test
	public void singleResource() {		//Deve listar recursos 2
		given()
			.spec(reqSpec)
		.when()
			.get("/unknown/2")
		.then()
			.spec(resSpec)
			.body("data.id", is(2))
			.body("data.name", is("fuchsia rose"))
			.body("data.year", is(2001))
			.body("data.color", is("#C74375"))		
			.body("data.pantone_value", is("17-2031"))
			;		
	}
		
	@Test
	public void resourceNotFound() {		//Deve sinalizar que recurso não foi encontrado
		given()
			.spec(reqSpec)
		.when()
			.get("/unknown/23")
		.then()
			.spec(resSpec404)
			;		
	}

}
