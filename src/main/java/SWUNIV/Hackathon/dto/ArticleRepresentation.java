package SWUNIV.Hackathon.dto;

import SWUNIV.Hackathon.entity.Article;
import SWUNIV.Hackathon.entity.Picture;
import SWUNIV.Hackathon.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Builder
public class ArticleRepresentation {
    @AllArgsConstructor
    static public class PictureRepresentation {
        Long id;
        String title;
        String description;
        String imageKey;
    }
    Long id;
    String authorName;
    String authorKakaoId;
    Long catId;
    String comment;
    List<PictureRepresentation> pictures;
    int vote;
    Date createdDate;

    public static ArticleRepresentation fromArticle(Article article) {
        User author = article.getAuthor();
        List<PictureRepresentation> pictures = new ArrayList<>();
        for (Picture pic : article.getPictures()) {
            pictures.add(new PictureRepresentation(pic.getId(), pic.getTitle(), pic.getDescription(), pic.getKey()));
        }
        return ArticleRepresentation.builder()
                .authorName(author.getName())
                .authorKakaoId(author.getKakaoID())
                .id(article.getId())
                .catId(article.getCat().getId())
                .comment(article.getComment())
                .vote(article.getVote())
                .createdDate(article.getCreatedDate())
                .pictures(pictures)
                .build();
    }
}
