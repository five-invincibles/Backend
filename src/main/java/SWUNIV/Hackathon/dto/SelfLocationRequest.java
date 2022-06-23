package SWUNIV.Hackathon.dto;

import SWUNIV.Hackathon.entity.DMS;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SelfLocationRequest {

    private DMS latitude;

    private DMS longitude;
}
