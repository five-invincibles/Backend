package SWUNIV.Hackathon.enumerations;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CatAge {

    lessOne("한 살 미만"),
    lessTen("한 살 이상 열 살 미만"),
    moreTen("열 살 이상"),
    unknown("미상");

    private final String value;
}
