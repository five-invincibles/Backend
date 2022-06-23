package SWUNIV.Hackathon.dto;

import SWUNIV.Hackathon.entity.Cat;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CatListResponse {

    private List<Cat> catList;
}
