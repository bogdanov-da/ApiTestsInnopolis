package apiTests.petstoreApiTests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import petstore.CreateUserResponseObject;
import petstore.Pet;
import petstore.User;

import java.util.ArrayList;
import java.util.List;

public class PetstoreTests {

	@Test
	void getUserTest() {
		User response = RestAssured
				.given()
				.log().all()
				.get("https://petstore.swagger.io/v2/user/user1").as(User.class);
		System.out.println(response.toString());
		//Assertions.assertEquals(200, response.statusCode());
		//Assertions.assertEquals("application/xml", response.contentType());
	}

	@Test
	void checkContentTypeInResponse() {
		Response response = RestAssured
				.given()
				.log().all()
				.header("Accept", "application/xml")
				.get("https://petstore.swagger.io/v2/user/user1");

		Assertions.assertEquals(200, response.statusCode());
		Assertions.assertEquals("application/xml", response.contentType());
	}

	@Test
	void createUserTest() {
		User actualUser = new User();
		String userName = actualUser.getUsername();
		CreateUserResponseObject response = RestAssured
				.given()
				.log().all()
				.body(actualUser)
				.header("Content-Type", "application/json")
				.post("https://petstore.swagger.io/v2/user").as(CreateUserResponseObject.class);

		User expectedUser = RestAssured
				.given()
				.log().all()
				.get("https://petstore.swagger.io/v2/user/" + userName).as(User.class);

		Assertions.assertEquals(200, response.getCode());
		Assertions.assertFalse(response.getType().isEmpty());
		Assertions.assertFalse(response.getMessage().isEmpty());
		Assertions.assertEquals(expectedUser.getUsername(), userName);
	}

	@Test
	void createUserByArrayTest() {
		User user = new User();
		User user2 = new User();
		List<User> userList = new ArrayList<>();
		userList.add(user);
		userList.add(user2);
		CreateUserResponseObject response = RestAssured
				.given()
				.log().all()
				.body(userList)
				.header("Content-Type", "application/json")
				.post("https://petstore.swagger.io/v2/user/createWithArray").as(CreateUserResponseObject.class);

		Assertions.assertEquals(200, response.getCode());
		Assertions.assertFalse(response.getType().isEmpty());
		Assertions.assertEquals("ok", response.getMessage());
	}

	@Test
	void deleteUserTest() {
		User actualUser = new User();
		String userName = actualUser.getUsername();
		RestAssured
				.given()
				.log().all()
				.body(actualUser)
				.header("Content-Type", "application/json")
				.post("https://petstore.swagger.io/v2/user").as(CreateUserResponseObject.class);

		RestAssured.given()
				.params("username", actualUser.getUsername(),"password", actualUser.getPassword())
				.get("https://petstore.swagger.io/v2/user/login");

		RestAssured.delete("https://petstore.swagger.io/v2/user/" + userName);
		Response expectedUser = RestAssured.get("https://petstore.swagger.io/v2/user/" + userName);

		Assertions.assertEquals(404, expectedUser.getStatusCode());
	}

	@Test
	public void getPetByPetId() {
		Pet pet = new Pet();

		Pet petResponse = RestAssured.given().body(pet).post("https://petstore.swagger.io/v2/pet/").as(Pet.class);
		System.out.println(petResponse);
	}
}
