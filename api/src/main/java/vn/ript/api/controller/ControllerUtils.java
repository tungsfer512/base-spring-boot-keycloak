package vn.ript.api.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import vn.ript.api.utils.Constants;
import vn.ript.api.utils.CustomHttpClientRequest;
import vn.ript.api.utils.CustomResponse;

public class ControllerUtils {

    public static CustomResponse<Object> request(
            @NotNull HttpMethod method,
            @NotNull String url,
            @Nullable Map<String, String> headers,
            @Nullable String type,
            @Nullable Map<String, String> params,
            @Nullable HttpEntity body) throws Exception {
        if (headers == null) {
            headers = new HashMap<>();
        }
        if (type == null) {
            type = "JSON";
        }
        CustomHttpClientRequest httpRequest = new CustomHttpClientRequest(method, url, headers);
        if (params != null && params.isEmpty() == false) {
            params.forEach((k, v) -> httpRequest.add_query_param(k, v));
        }
        HttpResponse httpResponse = null;
        if (body != null) {
            httpResponse = httpRequest.request(body);
        } else {
            httpResponse = httpRequest.request();
        }
        HttpEntity httpEntity = httpResponse.getEntity();
        String jsonResponse = null;
        InputStreamResource inputStreamResource = null;
        CustomResponse<Object> response = null;
        Integer status_code = httpResponse.getStatusLine().getStatusCode();
        if (httpEntity != null) {
            if (type.equalsIgnoreCase(Constants.LOAI_REQUEST.FILE.ma())) {
                inputStreamResource = new InputStreamResource(httpEntity.getContent());
                response = new CustomResponse<>(status_code, inputStreamResource);
            } else {
                jsonResponse = EntityUtils.toString(httpEntity);
                response = new CustomResponse<>(status_code, jsonResponse);
            }
        } else {
            response = new CustomResponse<>(status_code, null);
        }

        return response;
    }

    public static ResponseEntity<Object> response_error(Integer status_code, @Nullable Object obj) {
        if (obj != null) {
            CustomResponse<Object> response = new CustomResponse<>(status_code, obj.toString());
            return response.response();
        } else {
            CustomResponse<Object> response = new CustomResponse<>(status_code);
            return response.response();
        }
    }

}
