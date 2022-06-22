package SWUNIV.Hackathon.entity;

import SWUNIV.Hackathon.enumerations.Authority;
import SWUNIV.Hackathon.enumerations.University;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
public class User extends BaseEntity{

    private String name;

    private String kakaoID;

    @Setter
    private String accessToken;

    @Enumerated(EnumType.STRING)
    private Authority authority = Authority.USER;

    private String picturePath;

    private University university;

    private String email;
}
