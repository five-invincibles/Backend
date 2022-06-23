package SWUNIV.Hackathon.controller;

import SWUNIV.Hackathon.dto.BooleanResponse;
import SWUNIV.Hackathon.dto.CatRequest;
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
}
