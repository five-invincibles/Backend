package SWUNIV.Hackathon.dto;

import SWUNIV.Hackathon.entity.DMS;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LocationResponse {

    private DMS latitude;

    private DMS longitude;
}
