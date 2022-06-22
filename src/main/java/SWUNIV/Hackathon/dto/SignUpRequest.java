package SWUNIV.Hackathon.dto;

import SWUNIV.Hackathon.enumerations.Gender;
import java.util.Date;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class SignUpRequest {

    private String accessToken;

    private String university;
}
