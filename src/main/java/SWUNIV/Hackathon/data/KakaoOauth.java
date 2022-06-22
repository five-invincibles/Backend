package SWUNIV.Hackathon.data;

import SWUNIV.Hackathon.entity.User;
import SWUNIV.Hackathon.enumerations.Authority;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KakaoOauth {
    private Map<String, Object> attributes;
    private String name;
    private String email;
    private String picture;


    public static KakaoOauth of(Map<String, Object> attributes) {

        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> kakaoProfile = (Map<String, Object>) attributes.get("profile");

        return KakaoOauth.builder()
            .name((String) kakaoProfile.get("nickname"))
            .email((String) kakaoAccount.get("email"))
            .picture((String) kakaoProfile.get("profile_image_url"))
            .attributes(attributes)
            .build();
    }

    public User toEntity(){
        return User.builder()
            .name(name)
            .email(email)
            .picturePath(picture)
            .authority(Authority.GUEST)
            .build();
    }
}
