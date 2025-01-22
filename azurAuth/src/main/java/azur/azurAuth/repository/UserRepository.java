package azur.azurAuth.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import azur.azurAuth.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String userName);

}
