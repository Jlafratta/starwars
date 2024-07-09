package com.conexia.starwars.repository;

import com.conexia.starwars.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findFirstByApiKey(String apiKey);
}
