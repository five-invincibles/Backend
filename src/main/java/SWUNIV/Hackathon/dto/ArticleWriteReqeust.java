package SWUNIV.Hackathon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ArticleWriteReqeust {
    // 게시물 내용
    private String comment;
    // 업로드할 고양이게시판
    private Long catID;
    // 작성자
    private String kakaoID;
}
