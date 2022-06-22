package SWUNIV.Hackathon.dto;

import SWUNIV.Hackathon.auth.TokenResponse;
import SWUNIV.Hackathon.enumerations.Authority;
import SWUNIV.Hackathon.enumerations.Gender;
import SWUNIV.Hackathon.enumerations.University;
import java.util.Date;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private String name;

    private String picturePath;

    private University university;

    private String email;
}
