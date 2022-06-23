package SWUNIV.Hackathon.dto;

import SWUNIV.Hackathon.entity.DMS;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class PictureRequest {

    private DMS latitude;

    private DMS longitude;

    private String date;

    @Setter
    private Long catID;

    private String kakaoID;
}
