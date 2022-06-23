package SWUNIV.Hackathon.repository;

import SWUNIV.Hackathon.entity.Article;
import SWUNIV.Hackathon.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends CrudRepository<Article, Long> {
    Page<Article> findAll(Pageable pageable);
    Page<Article> findByAuthorKakaoID(String kakaoID, Pageable pageable);
    Page<Article> findByCatId(Long catId, Pageable pageable);
}
