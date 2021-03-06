package SWUNIV.Hackathon.dto;

import SWUNIV.Hackathon.enumerations.CatAge;
import SWUNIV.Hackathon.enumerations.CatSex;
import SWUNIV.Hackathon.enumerations.CatSpecies;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CatRequest {
    private String catName;

    private CatSpecies species;

    private CatAge age;

    private CatSex sex;

    private List<String> details;

    private PictureRequest pictureRequest;
}
