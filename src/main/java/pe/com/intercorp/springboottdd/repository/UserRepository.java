package pe.com.intercorp.springboottdd.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.com.intercorp.springboottdd.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public User findByUsername(String username);

}
