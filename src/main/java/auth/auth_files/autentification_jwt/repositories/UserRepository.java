package auth.auth_files.autentification_jwt.repositories;

import auth.auth_files.autentification_jwt.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findAll();

    User findUserByUserName(String username);
}

