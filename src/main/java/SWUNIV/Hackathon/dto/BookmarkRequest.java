package SWUNIV.Hackathon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BookmarkRequest {

    private String kakaoID;

    private String catName;
}
