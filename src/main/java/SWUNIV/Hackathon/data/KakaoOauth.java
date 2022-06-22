package SWUNIV.Hackathon.data;

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

    public static KakaoOauth of(String )
}
