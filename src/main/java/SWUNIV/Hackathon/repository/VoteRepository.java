package SWUNIV.Hackathon.repository;

import SWUNIV.Hackathon.entity.Article;
import SWUNIV.Hackathon.entity.User;
import SWUNIV.Hackathon.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findByUserAndArticle(User user, Article article);
}
