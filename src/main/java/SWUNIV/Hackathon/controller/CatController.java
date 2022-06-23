package SWUNIV.Hackathon.controller;

import SWUNIV.Hackathon.dto.BooleanResponse;
import SWUNIV.Hackathon.dto.CatDetailsResponse;
import SWUNIV.Hackathon.dto.CatUpdateRequest;
import SWUNIV.Hackathon.dto.FindCatRequest;
import SWUNIV.Hackathon.dto.CatListResponse;
import SWUNIV.Hackathon.dto.CatRequest;
import SWUNIV.Hackathon.dto.LocationResponse;
import SWUNIV.Hackathon.dto.SelfLocationRequest;
import SWUNIV.Hackathon.service.CatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cat")
public class CatController {

    private final CatService catService;

    @PostMapping(value = "/register", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<BooleanResponse> register(@RequestPart("file") MultipartFile file, @RequestPart CatRequest catRequest) {
        return ResponseEntity.ok().body(new BooleanResponse(catService.register(file, catRequest)));
    }

    @GetMapping("/details")
    ResponseEntity<CatDetailsResponse> details() {
        return ResponseEntity.ok().body(catService.getDetails());
    }

    @GetMapping("/location")
    ResponseEntity<LocationResponse> getRecentLocation(@RequestBody FindCatRequest findCatRequest) {
        return ResponseEntity.ok().body(catService.getRecentLocation(findCatRequest));
    }

    @GetMapping("/nearBy")
    ResponseEntity<CatListResponse> getCatListNearBy(@RequestBody SelfLocationRequest selfLocationRequest) {
        return ResponseEntity.ok().body(catService.getCatListNearBy(selfLocationRequest));
    }

    @PostMapping("/update")
    ResponseEntity<BooleanResponse> update(@RequestBody CatUpdateRequest catUpdateRequest) {
        return ResponseEntity.ok().body(new BooleanResponse(catService.update(catUpdateRequest)));
    }
}
