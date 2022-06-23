package SWUNIV.Hackathon.dto;

import SWUNIV.Hackathon.entity.DMS;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class PictureRequest {

    private String description;

    private String title;

    private DMS latitude;

    private DMS longitude;

    @Setter
    private Long catID;

    private String kakaoID;
}
