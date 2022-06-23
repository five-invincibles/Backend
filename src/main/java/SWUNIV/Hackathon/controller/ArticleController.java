package SWUNIV.Hackathon.controller;

import SWUNIV.Hackathon.dto.ArticleRepresentation;
import SWUNIV.Hackathon.dto.ArticleWriteReqeust;
import SWUNIV.Hackathon.dto.VoteRequest;
import SWUNIV.Hackathon.entity.Article;
import SWUNIV.Hackathon.repository.ArticleRepository;
import SWUNIV.Hackathon.service.ArticleService;
import SWUNIV.Hackathon.service.VoteService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@RestController
@NoArgsConstructor
@RequestMapping("/api/article")
public class ArticleController {
    private ArticleRepository articleRepository;
    private ArticleService articleService;

    @Autowired
    public void setBeans(
            ArticleRepository articleRepository,
            ArticleService articleService,
            VoteService voteService) {
        this.articleRepository = articleRepository;
        this.articleService = articleService;
    }

    @GetMapping("/list")
    public List<ArticleRepresentation> listAll(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size) {
        if (page == null) page = 0;
        if (size == null) size = 25;
        return articleRepository.findAll(PageRequest.of(page, size)).stream()
                .map(ArticleRepresentation::fromArticle)
                .collect(Collectors.toList());
    }

    @GetMapping("/list/author/{kakao_id}")
    public List<ArticleRepresentation> listForAuthor(
            @PathVariable("kakao_id") String kakaoID,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size) {
        if (page == null) page = 0;
        if (size == null) size = 25;
        return articleRepository.findByAuthorKakaoID(kakaoID, PageRequest.of(page, size)).stream()
                .map(ArticleRepresentation::fromArticle)
                .collect(Collectors.toList());
    }

    @GetMapping("/list/cat/{cat_id}")
    public List<ArticleRepresentation> listForCat(
            @PathVariable("cat_id") Long cat_id,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size) {
        if (page == null) page = 0;
        if (size == null) size = 25;
        return articleRepository.findByCatId(cat_id, PageRequest.of(page, size)).stream()
                .map(ArticleRepresentation::fromArticle)
                .collect(Collectors.toList());
    }

    @PostMapping("/write")
    public ResponseEntity<ArticleRepresentation> writeArticle(@RequestBody ArticleWriteReqeust writeReqeust)
            throws IllegalStateException {
        Article article = articleService.writeArticle(writeReqeust);
        return ResponseEntity.ok(ArticleRepresentation.fromArticle(article));
    }

    @DeleteMapping("/{article_id}")
    public ResponseEntity<Boolean> deleteArticle(
            @PathVariable("article_id") Long article_id,
            @RequestParam("kakao_id") String author_id) {
        return ResponseEntity.ok(articleService.deleteArticle(article_id, author_id));
    }

    @PostMapping("/vote/{article_id}")
    public ResponseEntity<Boolean> vote(@RequestBody VoteRequest voteRequest)
            throws IllegalStateException {
        return ResponseEntity.ok(articleService.vote(voteRequest));
    }

    @ExceptionHandler({IllegalStateException.class})
    public ResponseEntity<String> handle(Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
