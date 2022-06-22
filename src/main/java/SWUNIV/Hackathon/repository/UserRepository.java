package SWUNIV.Hackathon.repository;

import SWUNIV.Hackathon.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByEmail(String email);

    User findUserByEmail(String email);

    Boolean existsByNickname(String nickname);
}
