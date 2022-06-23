package SWUNIV.Hackathon.dto;

import SWUNIV.Hackathon.entity.DMS;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class PictureRequest {

    private String description;

    private String title;

    private DMS latitude;

    private DMS longitude;

    @Setter
    private Long catID;

    private String kakaoID;
}
