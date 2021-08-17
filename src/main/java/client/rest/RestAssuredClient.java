package client.rest;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static utils.Utils.takePause;

public class RestAssuredClient {
    private RequestSpecification requestSpecification;

    public RestAssuredClient(String url) {
        this.requestSpecification = new RequestSpecBuilder().setBaseUri(url).log(LogDetail.ALL).addFilter(new AllureRestAssured()).build();
    }

    @Step("GET")
    public Response get(String path) {
        takePause(5000);
        return RestAssured
                .given()
                .spec(requestSpecification)
                .basePath(path)
                .get();
    }

    @Step("GET")
    public Response get(String path, ContentType contentType) {
        takePause(5000);
        return RestAssured
                .given()
                .spec(requestSpecification)
                .accept(contentType)
                .basePath(path)
                .get();
    }

    @Step("POST")
    public Response post(Object body) {
        return RestAssured
                .given()
                .spec(requestSpecification)
                .contentType(ContentType.JSON)
                .body(body)
                .post();
    }

    @Step("POST")
    public Response post(Object body, String path) {
        return RestAssured
                .given()
                .spec(requestSpecification)
                .contentType(ContentType.JSON)
                .basePath(path)
                .body(body)
                .post();
    }

    @Step("DELETE")
    public void delete(String path) {
        takePause(5000);
        RestAssured.given().spec(requestSpecification).delete(path);
    }
}
