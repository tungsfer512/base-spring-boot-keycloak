package vn.ript.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import vn.ript.api.model.User;
import vn.ript.api.repository.UserRepository;
import vn.ript.api.utils.CustomSpecification;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public List<User> findWithConditions(
            String username,
            String email,
            String nickname,
            String firstName,
            String lastName,
            String gender,
            String phone,
            String birthDate,
            String address,
            String idCard,
            String idCardDate,
            String idCardPlace,
            String createdAt,
            String sortCreatedAt) {
        CustomSpecification<User> conditions = new CustomSpecification<>();
        List<Order> orders = new ArrayList<>();
        if (sortCreatedAt != null) {
            if (sortCreatedAt.equalsIgnoreCase("ASC")) {
                orders.add(new Order(Sort.Direction.ASC, "createdAt"));
            } else if (sortCreatedAt.equalsIgnoreCase("DESC")) {
                orders.add(new Order(Sort.Direction.DESC, "createdAt"));
            }
        }
        Sort sort = Sort.by(orders);

        return userRepository.findAll(conditions
                .and(conditions.condition("username", username, "like"))
                .and(conditions.condition("email", email, "like"))
                .and(conditions.condition("nickname", nickname, "like"))
                .and(conditions.condition("firstName", firstName, "like"))
                .and(conditions.condition("lastName", lastName, "like"))
                .and(conditions.condition("gender", gender, "equal"))
                .and(conditions.condition("phone", phone, "like"))
                .and(conditions.condition("birthDate", birthDate, "date_between"))
                .and(conditions.condition("address", address, "like"))
                .and(conditions.condition("idCard", idCard, "like"))
                .and(conditions.condition("idCardDate", idCardDate, "date_between"))
                .and(conditions.condition("idCardPlace", idCardPlace, "like"))
                .and(conditions.condition("createdAt", createdAt, "date_between")), sort);
    }

    public User findById(String id) {
        return userRepository.findById(id).orElse(null);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public void deleteById(String id) {
        userRepository.deleteById(id);
    }

}
