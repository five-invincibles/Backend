package SWUNIV.Hackathon.dto;

import SWUNIV.Hackathon.entity.DMS;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PictureRequest {

    private String description;

    private String title;

    private LocalDateTime uploadedDate;

    private DMS latitude;

    private DMS longitude;

    private Long catID;

    private String kakaoID;
}
