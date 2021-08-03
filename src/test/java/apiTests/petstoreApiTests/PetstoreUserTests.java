package apiTests.petstoreApiTests;

import client.RestAssuredClient;
import io.qameta.allure.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import petstore.CreateUserResponseObject;
import petstore.User;
import java.util.ArrayList;
import java.util.List;

@Epic("Petstore")
@Story("Petstore user tests")
@Feature("User Tests")
public class PetstoreUserTests {
	private RestAssuredClient apiClient = new RestAssuredClient("https://petstore.swagger.io/v2/user/");
	private User user;

	@AfterEach
	@Step("Clean user data")
	public void deleteUser() {
		if(user!=null) apiClient.delete(user.getUsername());
	}

	@RepeatedTest(1)
	void getUserTest() {
		user = new User();
		apiClient.post(user);
		User actualUser = apiClient.get(user.getUsername()).as(User.class);
		System.out.println(actualUser);
		Assertions.assertEquals(user, actualUser);
	}

	@RepeatedTest(3)
	@Description("Проверяем заголовок ContentType в ответе")
	void checkContentTypeInResponse() {
		Response response = apiClient.get("user1", ContentType.XML);
		Assertions.assertEquals(200, response.statusCode());
		Assertions.assertEquals("application/xml", response.contentType());
	}

	@Test
	@Description("Создаем нового пользователя, проверяем валидные поля")
	void createUserTest() {
		user = new User();
		CreateUserResponseObject response = apiClient.post(user).as(CreateUserResponseObject.class);
		Assertions.assertEquals(200, response.getCode());
		Assertions.assertFalse(response.getType().isEmpty());
		Assertions.assertFalse(response.getMessage().isEmpty());
	}

	@Test
	void createUserByArrayTest() {
		List<User> userList = getUserList();
		CreateUserResponseObject response = apiClient.post(userList, "createWithArray").as(CreateUserResponseObject.class);
		Assertions.assertEquals(200, response.getCode());
		Assertions.assertFalse(response.getType().isEmpty());
		Assertions.assertEquals("ok", response.getMessage());
	}

	@Test
	void deleteUserTest() {
		user = new User();
		String userName = user.getUsername();
		apiClient.post(user);
		apiClient.delete(userName);
		Assertions.assertEquals(200, apiClient.get(userName).getStatusCode());
	}

	private List<User> getUserList() {
		List<User> userList = new ArrayList<>();
		user = new User();
		userList.add(user);
		return userList;
	}
}
