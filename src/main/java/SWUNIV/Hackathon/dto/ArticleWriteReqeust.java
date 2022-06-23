package SWUNIV.Hackathon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ArticleWriteReqeust {
    // 게시물 내용
    private String comment;
    // 업로드할 고양이게시판
    private Long catId;
    // 포함된 사진의 목록
    private List<Long> imageIds;
    // 작성자
    private String kakaoID;
}
