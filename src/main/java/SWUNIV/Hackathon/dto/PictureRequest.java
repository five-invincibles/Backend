package SWUNIV.Hackathon.dto;

import SWUNIV.Hackathon.entity.Cat;
import SWUNIV.Hackathon.entity.DMS;
import java.time.LocalDateTime;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
