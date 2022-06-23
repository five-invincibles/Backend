package SWUNIV.Hackathon.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CatDetails {

    비만("비만"),
    링웜("링웜"),
    결막염("결막염"),
    임신("임신"),
    출산("출산"),
    대장고양이("대장고양이"),
    구토함("구토함"),
    기타("기타");

    private final String value;
}
