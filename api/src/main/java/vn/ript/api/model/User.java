package vn.ript.api.model;

import jakarta.persistence.*;

import lombok.*;

@Entity
@Table(name = "_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class User {

    @Id
    private String id;
    private String username;
    private String email;
    private String nickname;
    private String password;
    private String firstName;
    private String lastName;
    private String fullName;
    private String gender;
    private String phone;
    private String birthDate;
    private String address;
    private String idCard;
    private String idCardDate;
    private String idCardPlace;
    private String createdAt;

}
