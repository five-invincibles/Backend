package SWUNIV.Hackathon.dto;

import SWUNIV.Hackathon.enumerations.University;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.lang.Nullable;

@Getter
@AllArgsConstructor
public class UserUpdateRequest {

    private String kakaoID;

    @Nullable
    private String name;

    @Nullable
    private University university;

    @Nullable
    private String email;
}
