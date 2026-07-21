package main.java.com.ledgermesh.authservice.repository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, UUID> {
   
   Optional <User> findByEmail(String email);
   boolean existsByEmail (String email);
    
}
