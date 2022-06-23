package SWUNIV.Hackathon.controller;

import SWUNIV.Hackathon.entity.Article;
import SWUNIV.Hackathon.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/article")
public class ArticleController {
    @Autowired
    private final ArticleRepository articleRepository;

    @GetMapping("/list")
    public List<Article> listAll(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size) {
        if (page == null) page = 0;
        if (size == null) size = 25;
        return articleRepository.findAll(PageRequest.of(page, size)).toList();
    }

    @GetMapping("/list/{author_id}")
    public List<Article> listForAuthor(
            @PathVariable("author_id") String author_id,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size) {
        if (page == null) page = 0;
        if (size == null) size = 25;
        return articleRepository.findByAuthorKakaoID(author_id, PageRequest.of(page, size)).toList();
    }
}
