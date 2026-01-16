package dev.ved.natalis.user_service.repository;

import dev.ved.natalis.user_service.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User,String> {

    public Optional<User> findByPhoneNumber(String phoneNumber);

    public Optional<User> findByUsername(String username);

    public Optional<User> findByEmail(String email);
}
