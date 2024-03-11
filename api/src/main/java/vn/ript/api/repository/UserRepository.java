package vn.ript.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import vn.ript.api.model.User;

public interface UserRepository extends JpaRepository<User, String>,
                JpaSpecificationExecutor<User> {

}
