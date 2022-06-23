package SWUNIV.Hackathon.repository;

import SWUNIV.Hackathon.entity.Bookmark;
import SWUNIV.Hackathon.entity.Cat;
import SWUNIV.Hackathon.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    Bookmark findByCatAndUser(Cat cat, User user);
}
