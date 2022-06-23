package SWUNIV.Hackathon.enumerations;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CatSex {
    암컷("암컷"),
    수컷("수컷"),
    암컷중성화("암컷중성화"),
    수컷중성화("수컷중성화"),
    미상("미상");

    private final String value;
}
