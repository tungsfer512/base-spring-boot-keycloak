package vn.ript.api.controller;

import java.util.function.Function;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.ript.api.utils.CustomLogger;
import vn.ript.api.utils.CustomResponse;

@RestController
public class TestController {

    private static CustomLogger<TestController> logger = new CustomLogger<>(new TestController());

    @GetMapping("api/test-no-callback")
    public ResponseEntity<Object> testNoCallback() {
        Function<String, String> callback_success = null;
        Function<String, String> callback_fail = null;
        return ControllerUtils.testResponseForCallback(callback_success, callback_fail).response();
    }

    @GetMapping("api/test-callback")
    public ResponseEntity<Object> testCallback() {
        String message = "Hello, World";
        String success = "Success";
        String fail = "Fail";
        Function<String, String> callback_success = (arg) -> {
            logger.info(message + " ====== " + success);
            return arg;
        };
        Function<String, String> callback_fail = (arg) -> {
            logger.info(message + " ====== " + fail);
            return arg;
        };
        return ControllerUtils.testResponseForCallback(callback_success, callback_fail).response();
    }

    @GetMapping("api/test-callback-success")
    public ResponseEntity<Object> testCallbackSuccess() {
        String message = "Hello, World";
        String success = "Success";
        Function<String, String> callback_success = (arg) -> {
            logger.info(message + " ====== " + success);
            return arg;
        };
        Function<String, String> callback_fail = null;
        return ControllerUtils.testResponseForCallback(callback_success, callback_fail).response();
    }

    @GetMapping("api/test-callback-fail")
    public ResponseEntity<Object> testCallbackFail() {
        String message = "Hello, World";
        String fail = "Fail";
        Function<String, String> callback_success = null;
        Function<String, String> callback_fail = (arg) -> {
            logger.info(message + " ====== " + fail);
            return arg;
        };
        return ControllerUtils.testResponseForCallback(callback_success, callback_fail).response();
    }

    @GetMapping("api/test-auth")
    public ResponseEntity<Object> testAuth() {
        return ControllerUtils.response(200, "Test Auth").response();
    }

    @GetMapping("api/test-auth-user")
    public ResponseEntity<Object> testAuthUser() {
        return ControllerUtils.response(200, "Test Auth User").response();
    }

    @GetMapping("api/test-auth-admin")
    public ResponseEntity<Object> testAuthAdmin() {
        return ControllerUtils.response(200, "Test Auth Admin").response();
    }

    @GetMapping("api/test-no-auth")
    public ResponseEntity<Object> testNoAuth() {
        return ControllerUtils.response(200, "Test No Auth").response();
    }

}
