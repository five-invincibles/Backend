package SWUNIV.Hackathon.enumerations;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CatSpecies {

    치즈("치즈"),
    치즈태비("치즈태비"),
    고등어("고등어"),
    고등어태비("고등어태비"),
    삼색("삼색"),
    삼색태비("삼색태비"),
    카오스("카오스"),
    하양("하양"),
    얼룩("얼룩"),
    깻잎("깻잎"),
    까망("까망"),
    정장("정장"),
    샴("샴");

    private final String value;
}