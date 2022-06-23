package SWUNIV.Hackathon.service;

import SWUNIV.Hackathon.dto.ArticleWriteReqeust;
import SWUNIV.Hackathon.entity.Article;
import SWUNIV.Hackathon.entity.Cat;
import SWUNIV.Hackathon.entity.User;
import SWUNIV.Hackathon.repository.ArticleRepository;
import SWUNIV.Hackathon.repository.CatRepository;
import SWUNIV.Hackathon.repository.UserRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@NoArgsConstructor
public class ArticleService {
    private ArticleRepository articleRepository;
    private UserRepository userRepository;
    private CatRepository catRepository;

    @Autowired
    public void setRepositories(
            CatRepository catRepository,
            UserRepository userRepository,
            ArticleRepository articleRepository) {
        this.catRepository = catRepository;
        this.userRepository = userRepository;
        this.articleRepository = articleRepository;
    }

    public Article writeArticle(ArticleWriteReqeust writeReqeust) {
        User author = userRepository.findUserByKakaoID(writeReqeust.getKakaoID());
        if (author == null) {
            throw new IllegalStateException("No such user.");
        }
        Cat cat = catRepository.findById(writeReqeust.getCatID())
                .orElseThrow(() -> new IllegalStateException("No such cat."));
        Article article =
                Article.builder()
                        .cat(cat).author(author).comment(writeReqeust.getComment())
                        .build();
        return articleRepository.save(article);
    }

    public boolean deleteArticle(Long article_id, String author_id) {
        Optional<Long> oArticleId = articleRepository.findById(article_id)
                .map(article->article.getAuthor().getKakaoID())
                .flatMap((String id) -> {
                    if (author_id.equals(id))
                        return Optional.of(article_id);
                    else
                        return Optional.empty();
                });
        // article_id가 존재하고 article의 작성자와 author_id가 같은 경우에만 이 옵셔널이 present이다.
        oArticleId.ifPresent((id) ->
                articleRepository.deleteById(article_id));
        return oArticleId.isPresent();
    }
}
