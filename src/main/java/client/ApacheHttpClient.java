package client;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;

public class ApacheHttpClient {

    public HttpClient getHttpClient() {
        return HttpClients.createDefault();
    }

    public HttpResponse getHttpResponse(HttpClient httpClient, HttpUriRequest request) {
        HttpResponse httpResponse = null;
        try {
            httpResponse = httpClient.execute(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return httpResponse;
    }
}
