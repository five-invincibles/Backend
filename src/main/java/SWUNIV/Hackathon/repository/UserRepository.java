package SWUNIV.Hackathon.repository;

import SWUNIV.Hackathon.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByEmail(String email);

    User findUserByEmail(String email);


    User findUserByKakaoID(String kakaoID);
}
