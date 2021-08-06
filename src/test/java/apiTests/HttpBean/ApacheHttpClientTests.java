package apiTests.HttpBean;

import apiTests.TestHelper;
import client.ApacheHttpClient;
import httpBean.HttpBeanPojo;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class ApacheHttpClientTests {

    private ApacheHttpClient client = new ApacheHttpClient();
    private final static String BASE_URL = TestHelper.getProperty("httpBeanBaseUrl");
    private HttpClient httpClient;

    @Test
    void check200StatusCodeAndHeadersInHttpBeanGetRequest() {
        httpClient = client.getHttpClient();
        HttpGet httpGet = new HttpGet(BASE_URL + "get");
        httpGet.addHeader("Accept", "application/json");
        HttpResponse httpResponse = client.getHttpResponse(httpClient, httpGet);
        HttpBeanPojo httpBeanPojo = (HttpBeanPojo) TestHelper.getObject(httpResponse, HttpBeanPojo.class);
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(httpResponse.getStatusLine().getStatusCode()).isEqualTo(200);
        softly.assertThat(httpBeanPojo.getHeaders().getAccept()).isEqualTo("application/json");
        softly.assertThat(httpBeanPojo.getHeaders().getHost()).isEqualTo("httpbin.org");
        softly.assertAll();
    }

    @Test
    void checkFormInHttpBeanPostRequest() {
        httpClient = client.getHttpClient();
        HttpPost httpPost = new HttpPost(BASE_URL + "post");
        fillParameters(httpPost);
        HttpResponse httpResponse = client.getHttpResponse(httpClient, httpPost);
        HttpBeanPojo httpBeanPojo = (HttpBeanPojo) TestHelper.getObject(httpResponse, HttpBeanPojo.class);
        Assertions.assertEquals("{name=value}", httpBeanPojo.getArgs().get("form").toString());
    }

    @Test
    void checkJsonInHttpBeanPostRequestWithBody() {
        httpClient = client.getHttpClient();
        HttpPost httpPost = new HttpPost(BASE_URL + "post");
        fillBody(httpPost);
        HttpResponse httpResponse = client.getHttpResponse(httpClient, httpPost);
        HttpBeanPojo httpBeanPojo = (HttpBeanPojo) TestHelper.getObject(httpResponse, HttpBeanPojo.class);
        Assertions.assertEquals("{name=value}", httpBeanPojo.getArgs().get("json").toString());
    }    

    @Test
    public void check200InHttpBeanRequestWithCredentials() {
        String login = "login";
        String password = "password";
        HttpGet httpGet = new HttpGet(BASE_URL + "basic-auth/" + login + "/" + password);
        CredentialsProvider credentialsProvider = getCredentialsProvider(login, password);
        httpClient = HttpClientBuilder.create().setDefaultCredentialsProvider(credentialsProvider).build();
        HttpResponse httpResponse = client.getHttpResponse(httpClient, httpGet);
        Assertions.assertEquals(200, httpResponse.getStatusLine().getStatusCode());
    }

    private CredentialsProvider getCredentialsProvider(String login, String password) {
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(login, password));
        return credentialsProvider;
    }

    private void fillBody(HttpPost httpPost) {
        try {
            StringEntity stringEntity = new StringEntity("{\"name\":\"value\"}");
            httpPost.setEntity(stringEntity);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void fillParameters(HttpPost httpPost) {
        List<NameValuePair> list = new ArrayList<>();
        list.add(new BasicNameValuePair("name", "value"));
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(list));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
