package SWUNIV.Hackathon.dto;


import SWUNIV.Hackathon.enumerations.CatAge;
import SWUNIV.Hackathon.enumerations.CatSex;
import SWUNIV.Hackathon.enumerations.CatSpecies;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.lang.Nullable;

@Getter
@AllArgsConstructor
public class CatUpdateRequest {

    private String catName;

    @Nullable
    private CatSpecies species;

    @Nullable
    private CatAge age;

    @Nullable
    private CatSex sex;

    @Nullable
    private List<String> details;
}

