package SWUNIV.Hackathon.dto;

import SWUNIV.Hackathon.entity.DMS;
import SWUNIV.Hackathon.entity.User;
import SWUNIV.Hackathon.enumerations.CatAge;
import SWUNIV.Hackathon.enumerations.CatSex;
import SWUNIV.Hackathon.enumerations.CatSpecies;
import java.time.LocalDateTime;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class CatRequest {
    private String catName;

    private CatSpecies species;

    private CatAge age;

    private CatSex sex;

    private String details;

    private DMS latitude;

    private DMS longitude;

    private String kakaoID;
}
