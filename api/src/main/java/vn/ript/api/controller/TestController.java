package vn.ript.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.ript.api.utils.CustomLogger;
import vn.ript.api.utils.CustomResponse;

@RestController
public class TestController {

    private static CustomLogger<TestController> logger = new CustomLogger<>(new TestController());

    @GetMapping("/api/test-no-callback")
    public ResponseEntity<Object> testNoCallback() {
        Runnable callback_success = null;
        Runnable callback_fail = null;
        return ControllerUtils.response(callback_success, callback_fail);
    }

    @GetMapping("/api/test-callback")
    public ResponseEntity<Object> testCallback() {
        String message = "Hello, World";
        String success = "Success";
        String fail = "Fail";
        Runnable callback_success = () -> {
            logger.info(message + " ====== " + success);
        };
        Runnable callback_fail = () -> {
            logger.info(message + " ====== " + fail);
        };
        return ControllerUtils.response(callback_success, callback_fail);
    }

    @GetMapping("/api/test-callback-success")
    public ResponseEntity<Object> testCallbackSuccess() {
        String message = "Hello, World";
        String success = "Success";
        Runnable callback_success = () -> {
            logger.info(message + " ====== " + success);
        };
        Runnable callback_fail = null;
        return ControllerUtils.response(callback_success, callback_fail);
    }

    @GetMapping("/api/test-callback-fail")
    public ResponseEntity<Object> testCallbackFail() {
        String message = "Hello, World";
        String fail = "Fail";
        Runnable callback_success = null;
        Runnable callback_fail = () -> {
            logger.info(message + " ====== " + fail);
        };
        return ControllerUtils.response(callback_success, callback_fail);
    }

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
