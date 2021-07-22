package apiTests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class JsonplaceholderGetTests {

	@Test
	void checkStatusCodeInGetPostsResponse() {
		Response response = RestAssured.get("http://jsonplaceholder.typicode.com/posts");
		Assertions.assertEquals(200, response.statusCode());
	}

	//TODO add paramValue checking in response
	@Test
	void checkStatusCodeInGetCommentsResponse() {
		String paramValue = "1";
		Response response = RestAssured
				.given()
				.baseUri("https://jsonplaceholder.typicode.com")
				.basePath("comments")
				.param("postId", paramValue)
				.get();

		Assertions.assertEquals(200, response.statusCode());
		//Assertions.assertEquals(paramValue, "response.postId");
	}

	@Test
	void checkStatusCodeInGetCommentsResponseWithPostIdAndIdInParams() {
		String paramValue = "1";
		Response response = RestAssured
				.given()
				.log().all()
				.baseUri("https://jsonplaceholder.typicode.com")
				.basePath("comments")
				.params("postId", paramValue, "id", 2)
				.get();

		Assertions.assertEquals(200, response.statusCode());
		//Assertions.assertEquals(paramValue, "response.postId");
	}
}
