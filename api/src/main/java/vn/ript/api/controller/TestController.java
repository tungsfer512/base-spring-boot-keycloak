package vn.ript.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.ript.api.utils.CustomResponse;

@RestController
public class TestController {

    @GetMapping("/api/test-auth")
    public ResponseEntity<Object> testAuth() {
        CustomResponse<String> response = new CustomResponse<String>(200, "Test Auth");
        return response.response();
    }

    @GetMapping("/api/test-auth-user")
    public ResponseEntity<Object> testAuthUser() {
        CustomResponse<String> response = new CustomResponse<String>(200, "Test Auth User");
        return response.response();
    }

    @GetMapping("/api/test-auth-admin")
    public ResponseEntity<Object> testAuthAdmin() {
        CustomResponse<String> response = new CustomResponse<String>(200, "Test Auth Admin");
        return response.response();
    }

    @GetMapping("/api/test-no-auth")
    public ResponseEntity<Object> testNoAuth() {
        CustomResponse<String> response = new CustomResponse<String>(200, "Test No Auth");
        return response.response();
    }

}
