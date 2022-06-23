package SWUNIV.Hackathon.controller;

import SWUNIV.Hackathon.dto.ArticleWriteReqeust;
import SWUNIV.Hackathon.entity.Article;
import SWUNIV.Hackathon.repository.ArticleRepository;
import SWUNIV.Hackathon.service.ArticleService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@NoArgsConstructor
@RequestMapping("/api/article")
public class ArticleController {
    private ArticleRepository articleRepository;
    private ArticleService articleService;

    @Autowired
    public void setBeans(
            ArticleRepository articleRepository,
            ArticleService articleService) {
        this.articleRepository = articleRepository;
        this.articleService = articleService;
    }

    @GetMapping("/list")
    public List<Article> listAll(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size) {
        if (page == null) page = 0;
        if (size == null) size = 25;
        return articleRepository.findAll(PageRequest.of(page, size)).toList();
    }

    @GetMapping("/list/author/{kakao_id}")
    public List<Article> listForAuthor(
            @PathVariable("kakao_id") String kakaoID,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size) {
        if (page == null) page = 0;
        if (size == null) size = 25;
        return articleRepository.findByAuthorKakaoID(kakaoID, PageRequest.of(page, size)).toList();
    }

    @GetMapping("/list/cat/{cat_id}")
    public List<Article> listForCat(
            @PathVariable("cat_id") Long cat_id,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size) {
        if (page == null) page = 0;
        if (size == null) size = 25;
        return articleRepository.findByCatId(cat_id, PageRequest.of(page, size)).toList();
    }

    @PostMapping("/write")
    public ResponseEntity<Long> writeArticle(@RequestBody ArticleWriteReqeust writeReqeust) {
        try {
            return ResponseEntity.ok(articleService.writeArticle(writeReqeust).getId());
        } catch (IllegalStateException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{article_id}")
    public ResponseEntity<Boolean> deleteArticle(
            @PathVariable("article_id") Long article_id,
            @RequestParam("kakao_id") String author_id) {
        return ResponseEntity.ok(articleService.deleteArticle(article_id, author_id));
    }
}
