package vn.ript.api.utils;

import org.json.JSONObject;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class CustomResponse<T> {

    T data;
    Integer statusCode;

    public CustomResponse(int status) {
        this.statusCode = status;
        this.data = null;
    }

    public CustomResponse(int status, T data) {
        this.statusCode = status;
        this.data = data;
    }

    public ResponseEntity<Object> response() {
        HttpStatus httpStatus = HttpStatus.valueOf(this.statusCode);
        JSONObject responseData = new JSONObject();
        if (httpStatus.is2xxSuccessful()) {
            responseData.put("ErrorDesc", "Thanh cong");
            responseData.put("ErrorCode", "0");
            responseData.put("status", "OK");
            responseData.put("data", this.data);
            return new ResponseEntity<Object>(responseData.toMap(), httpStatus);
        } else {
            responseData.put("ErrorDesc", "That bai");
            responseData.put("ErrorCode", "-1");
            responseData.put("status", "FAILED");
            responseData.put("error", this.data);
            return new ResponseEntity<Object>(responseData.toMap(), httpStatus);
        }
    }

    public ResponseEntity<Object> response_file(String filename) {
        JSONObject responseData = new JSONObject();
        responseData.put("ErrorDesc", "That bai");
        responseData.put("ErrorCode", "-1");
        responseData.put("status", "FAILED");
        responseData.put("error", "Loi file!!");
        HttpStatus httpStatus = HttpStatus.valueOf(this.statusCode);
        if (httpStatus.is2xxSuccessful()) {
            if (data instanceof InputStreamResource) {
                return ResponseEntity.status(this.statusCode)
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + filename)
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .body(this.data);
            } else {
                return new ResponseEntity<Object>(responseData, httpStatus);
            }
        } else {
            return new ResponseEntity<Object>(responseData, httpStatus);
        }
    }

    public Integer getStatusCode() {
        return this.statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
