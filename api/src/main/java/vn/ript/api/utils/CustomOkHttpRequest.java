package vn.ript.api.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CustomOkHttpRequest {

    String method;
    HttpUrl.Builder urlBuilder;
    Map<String, String> headers;

    public CustomOkHttpRequest(String method, String url) {
        this.method = method;
        this.urlBuilder = HttpUrl.parse(url).newBuilder();
        this.headers = new HashMap<>();
    }

    public CustomOkHttpRequest(String method, String url, Map<String, String> headers) {
        this.method = method;
        this.urlBuilder = HttpUrl.parse(url).newBuilder();
        this.headers = headers;
    }

    public void add_query_param(String key, Object value) {
        if (value != null) {
            this.urlBuilder.addQueryParameter(key, value.toString());
        }
    }

    public Response request() {
        try {
            OkHttpClient client = SkipSSLClient.CreateOkHtpClient();
            Request.Builder requestBuilder = new Request.Builder().url(this.urlBuilder.build());
            requestBuilder.method(this.method.toUpperCase(), null);
            for (Map.Entry<String, String> entry : this.headers.entrySet()) {
                requestBuilder.addHeader(entry.getKey(), entry.getValue());
            }
            Request request = requestBuilder.build();
            Call call = client.newCall(request);
            Response response = call.execute();
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Response request(RequestBody entity) {
        try {
            OkHttpClient client = SkipSSLClient.CreateOkHtpClient();
            Request.Builder requestBuilder = new Request.Builder().url(this.urlBuilder.build());
            requestBuilder.method(this.method.toUpperCase(), entity);
            for (Map.Entry<String, String> entry : this.headers.entrySet()) {
                requestBuilder.addHeader(entry.getKey(), entry.getValue());
            }
            Request request = requestBuilder.build();
            Call call = client.newCall(request);
            Response response = call.execute();
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
