package apiTests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class HttpBeanTests {

	@Test
	void checkValidBasicAuth() {
		Response response = RestAssured
				.given()
				.log().all()
				.auth().basic("123", "123")
				.get("http://httpbin.org/basic-auth/123/123");

		Assertions.assertEquals(200, response.statusCode());
	}

	@Test
	void checkInvalidBasicAuth() {
		Response response = RestAssured
				.given()
				.log().all()
				.auth().basic("555", "123")
				.get("http://httpbin.org/basic-auth/123/123");

		Assertions.assertEquals(401, response.statusCode());
	}

	@Test
	void checkValidBearerAuth() {
		Response response = RestAssured
				.given()
				.log().all()
				.header("Authorization", "Bearer 123")
				.get("http://httpbin.org/bearer");

		Assertions.assertEquals(200, response.statusCode());
	}

	@Test
	void checkInvalidBearerAuth() {
		Response response = RestAssured
				.given()
				.log().all()
				.header("Authorization", "Bearer")
				.get("http://httpbin.org/bearer");

		Assertions.assertEquals(401, response.statusCode());
	}
}
