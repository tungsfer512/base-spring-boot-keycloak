package vn.ript.api.controller;

import java.util.List;
import java.util.Map;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.ws.rs.core.Response;
import vn.ript.api.model.User;
import vn.ript.api.service.UserService;
import vn.ript.api.utils.CustomResponse;
import vn.ript.api.utils.Env;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private static String realm = Env.KEYCLOAK_REALM;

    @Autowired
    private Keycloak keycloak;

    @Autowired
    UserService userService;

    @GetMapping("")
    public ResponseEntity<Object> getAll(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String nickname,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String birthDate,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) String idCard,
            @RequestParam(required = false) String idCardDate,
            @RequestParam(required = false) String idCardPlace,
            @RequestParam(required = false) String createdAt,
            @RequestParam(required = false) String sortCreatedAt) {
        try {
            List<UserRepresentation> users = keycloak.realm(realm).users().list();
            List<String> userIds = users.stream().map(user -> user.getId()).toList();
            List<User> localUsers = userService.findAll();
            List<String> localUserIds = localUsers.stream().map(user -> user.getId()).toList();
            localUserIds.forEach(id -> {
                if (!userIds.contains(id)) {
                    userService.deleteById(id);
                }
            });
            users.forEach(user -> {
                if (!localUserIds.contains(user.getId())) {
                    User newUser = new User();
                    newUser.setId(user.getId());
                    newUser.setUsername(user.getUsername());
                    newUser.setEmail(user.getEmail());
                    newUser.setFirstName(user.getFirstName());
                    newUser.setLastName(user.getLastName());
                    newUser.setFullName(user.getLastName() + " " + user.getFirstName());
                    newUser.setCreatedAt(user.getCreatedTimestamp().toString());
                    userService.save(newUser);
                }
            });
            List<User> resUsers = userService.findWithConditions(
                    username,
                    email,
                    nickname,
                    firstName,
                    lastName,
                    gender,
                    phone,
                    birthDate,
                    address,
                    idCard,
                    idCardDate,
                    idCardPlace,
                    createdAt,
                    sortCreatedAt);
            CustomResponse<List<User>> response = new CustomResponse<>(200, resUsers);
            return response.response();
        } catch (Exception e) {
            CustomResponse<Object> response = new CustomResponse<>(500, e);
            return response.response();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable String id) {
        try {
            List<UserRepresentation> users = keycloak.realm(realm).users().list();
            List<String> userIds = users.stream().map(user -> user.getId()).toList();
            if (userIds.contains(id)) {
                User user = userService.findById(id);
                if (user == null) {
                    UserRepresentation keycloakUser = keycloak.realm(realm).users().get(id).toRepresentation();
                    user = new User();
                    user.setId(keycloakUser.getId());
                    user.setUsername(keycloakUser.getUsername());
                    user.setEmail(keycloakUser.getEmail());
                    user.setFirstName(keycloakUser.getFirstName());
                    user.setLastName(keycloakUser.getLastName());
                    user.setFullName(keycloakUser.getLastName() + " " +
                            keycloakUser.getFirstName());
                    user.setCreatedAt(keycloakUser.getCreatedTimestamp().toString());
                    userService.save(user);
                }
                User resUser = userService.findById(id);
                CustomResponse<User> response = new CustomResponse<>(200, resUser);
                return response.response();
            } else {
                User user = userService.findById(id);
                if (user != null) {
                    userService.deleteById(id);
                }
                CustomResponse<Object> response = new CustomResponse<>(404);
                return response.response();
            }
        } catch (Exception e) {
            CustomResponse<Object> response = new CustomResponse<>(500, e);
            return response.response();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateById(@PathVariable String id,
            @RequestBody Map<String, Object> body) {
        try {
            List<UserRepresentation> users = keycloak.realm(realm).users().list();
            List<String> userIds = users.stream().map(user -> user.getId()).toList();
            if (userIds.contains(id)) {
                User user = userService.findById(id);
                if (user == null) {
                    user = new User();
                }
                UserResource keycloakUserResource = keycloak.realm(realm).users().get(id);
                UserRepresentation keycloakUser = keycloakUserResource.toRepresentation();
                if (body.containsKey("nickname")) {
                    user.setNickname(body.get("nickname").toString());
                }
                if (body.containsKey("firstName")) {
                    user.setFirstName(body.get("firstName").toString());
                    user.setFullName((user.getLastName() + " " +
                            body.get("firstName").toString()).trim());
                    keycloakUser.setFirstName(body.get("firstName").toString());
                }
                if (body.containsKey("lastName")) {
                    user.setLastName(body.get("lastName").toString());
                    user.setFullName((body.get("lastName").toString() + " " +
                            user.getFirstName()).trim());
                    keycloakUser.setLastName(body.get("lastName").toString());
                }
                if (body.containsKey("gender")) {
                    user.setGender(body.get("gender").toString());
                }
                if (body.containsKey("phone")) {
                    user.setPhone(body.get("phone").toString());
                }
                if (body.containsKey("birthDate")) {
                    user.setBirthDate(body.get("birthDate").toString());
                }
                if (body.containsKey("address")) {
                    user.setAddress(body.get("address").toString());
                }
                if (body.containsKey("idCard")) {
                    user.setIdCard(body.get("idCard").toString());
                }
                if (body.containsKey("idCardDate")) {
                    user.setIdCardDate(body.get("idCardDate").toString());
                }
                if (body.containsKey("idCardPlace")) {
                    user.setIdCardPlace(body.get("idCardPlace").toString());
                }
                user.setCreatedAt(keycloakUser.getCreatedTimestamp().toString());
                keycloakUserResource.update(keycloakUser);
                if (body.containsKey("password")) {
                    CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
                    credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
                    credentialRepresentation.setValue(body.get("password").toString().trim());
                    keycloakUserResource.resetPassword(credentialRepresentation);
                }
                userService.save(user);
                User resUser = userService.findById(id);
                CustomResponse<User> response = new CustomResponse<>(200, resUser);
                return response.response();
            } else {
                User user = userService.findById(id);
                if (user != null) {
                    userService.deleteById(id);
                }
                CustomResponse<Object> response = new CustomResponse<>(404);
                return response.response();
            }
        } catch (Exception e) {
            CustomResponse<Object> response = new CustomResponse<>(500, e);
            return response.response();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable String id) {
        try {
            List<UserRepresentation> users = keycloak.realm(realm).users().list();
            List<String> userIds = users.stream().map(user -> user.getId()).toList();
            if (userIds.contains(id)) {
                Response keycloakResponse = keycloak.realm(realm).users().delete(id);
                if (!HttpStatus.valueOf(keycloakResponse.getStatus()).is2xxSuccessful()) {
                    CustomResponse<Object> response = new CustomResponse<>(keycloakResponse.getStatus(),
                            keycloakResponse.getStatusInfo().getReasonPhrase());
                    return response.response();
                }
            }
            User user = userService.findById(id);
            if (user != null) {
                userService.deleteById(id);
            }
            CustomResponse<Object> response = new CustomResponse<>(204);
            return response.response();
        } catch (Exception e) {
            CustomResponse<Object> response = new CustomResponse<>(500, e);
            return response.response();
        }
    }

    @GetMapping("/me")
    public ResponseEntity<Object> getMe(@AuthenticationPrincipal Jwt jwt) {
        try {
            String id = jwt.getClaim("sub");
            User user = userService.findById(id);
            if (user == null) {
                UserRepresentation keycloakUser = keycloak.realm(realm).users().get(id).toRepresentation();
                user = new User();
                user.setId(keycloakUser.getId());
                user.setUsername(keycloakUser.getUsername());
                user.setEmail(keycloakUser.getEmail());
                user.setFirstName(keycloakUser.getFirstName());
                user.setLastName(keycloakUser.getLastName());
                user.setFullName(keycloakUser.getLastName() + " " +
                        keycloakUser.getFirstName());
                user.setCreatedAt(keycloakUser.getCreatedTimestamp().toString());
                userService.save(user);
            }
            User resUser = userService.findById(id);
            CustomResponse<User> response = new CustomResponse<>(200, resUser);
            return response.response();
        } catch (Exception e) {
            CustomResponse<Object> response = new CustomResponse<>(500, e);
            return response.response();
        }
    }

    @GetMapping("/me/permissions")
    public ResponseEntity<Object> getMePermissions(@AuthenticationPrincipal Jwt jwt) {
        try {
            String id = jwt.getClaim("sub");
            User user = userService.findById(id);
            if (user == null) {
                UserRepresentation keycloakUser = keycloak.realm(realm).users().get(id).toRepresentation();
                user = new User();
                user.setId(keycloakUser.getId());
                user.setUsername(keycloakUser.getUsername());
                user.setEmail(keycloakUser.getEmail());
                user.setFirstName(keycloakUser.getFirstName());
                user.setLastName(keycloakUser.getLastName());
                user.setFullName(keycloakUser.getLastName() + " " +
                        keycloakUser.getFirstName());
                user.setCreatedAt(keycloakUser.getCreatedTimestamp().toString());
                userService.save(user);
            }
            CustomResponse<Map<String, Object>> response = new CustomResponse<>(200,
                    jwt.getClaims());

            return response.response();
        } catch (Exception e) {
            CustomResponse<Object> response = new CustomResponse<>(500, e);
            return response.response();
        }
    }

    @PatchMapping("/me")
    public ResponseEntity<Object> updateMe(@AuthenticationPrincipal Jwt jwt,
            @RequestBody Map<String, Object> body) {
        try {
            List<UserRepresentation> users = keycloak.realm(realm).users().list();
            List<String> userIds = users.stream().map(user -> user.getId()).toList();
            String id = jwt.getClaim("sub");
            if (userIds.contains(id)) {
                User user = userService.findById(id);
                if (user == null) {
                    user = new User();
                }
                UserResource keycloakUserResource = keycloak.realm(realm).users().get(id);
                UserRepresentation keycloakUser = keycloakUserResource.toRepresentation();
                if (body.containsKey("nickname")) {
                    user.setNickname(body.get("nickname").toString());
                }
                if (body.containsKey("firstName")) {
                    user.setFirstName(body.get("firstName").toString());
                    user.setFullName((user.getLastName() + " " +
                            body.get("firstName").toString()).trim());
                    keycloakUser.setFirstName(body.get("firstName").toString());
                }
                if (body.containsKey("lastName")) {
                    user.setLastName(body.get("lastName").toString());
                    user.setFullName((body.get("lastName").toString() + " " +
                            user.getFirstName()).trim());
                    keycloakUser.setLastName(body.get("lastName").toString());
                }
                if (body.containsKey("gender")) {
                    user.setGender(body.get("gender").toString());
                }
                if (body.containsKey("phone")) {
                    user.setPhone(body.get("phone").toString());
                }
                if (body.containsKey("birthDate")) {
                    user.setBirthDate(body.get("birthDate").toString());
                }
                if (body.containsKey("address")) {
                    user.setAddress(body.get("address").toString());
                }
                if (body.containsKey("idCard")) {
                    user.setIdCard(body.get("idCard").toString());
                }
                if (body.containsKey("idCardDate")) {
                    user.setIdCardDate(body.get("idCardDate").toString());
                }
                if (body.containsKey("idCardPlace")) {
                    user.setIdCardPlace(body.get("idCardPlace").toString());
                }
                user.setCreatedAt(keycloakUser.getCreatedTimestamp().toString());
                keycloakUserResource.update(keycloakUser);
                if (body.containsKey("password")) {
                    CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
                    credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
                    credentialRepresentation.setValue(body.get("password").toString().trim());
                    keycloakUserResource.resetPassword(credentialRepresentation);
                }
                userService.save(user);
                User resUser = userService.findById(id);
                CustomResponse<User> response = new CustomResponse<>(200, resUser);
                return response.response();
            } else {
                User user = userService.findById(id);
                if (user != null) {
                    userService.deleteById(id);
                }
                CustomResponse<Object> response = new CustomResponse<>(404);
                return response.response();
            }
        } catch (Exception e) {
            CustomResponse<Object> response = new CustomResponse<>(500, e);
            return response.response();
        }
    }

}
