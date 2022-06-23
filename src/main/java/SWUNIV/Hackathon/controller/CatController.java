package SWUNIV.Hackathon.controller;

import SWUNIV.Hackathon.dto.BookmarkRequest;
import SWUNIV.Hackathon.dto.BooleanResponse;
import SWUNIV.Hackathon.dto.CatDetailsResponse;
import SWUNIV.Hackathon.dto.CatUpdateRequest;
import SWUNIV.Hackathon.dto.CatNameRequest;
import SWUNIV.Hackathon.dto.CatListResponse;
import SWUNIV.Hackathon.dto.CatRequest;
import SWUNIV.Hackathon.dto.LocationResponse;
import SWUNIV.Hackathon.dto.SelfLocationRequest;
import SWUNIV.Hackathon.entity.Bookmark;
import SWUNIV.Hackathon.entity.Cat;
import SWUNIV.Hackathon.service.CatService;
import java.util.List;
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

    @PostMapping("/location")
    ResponseEntity<LocationResponse> getRecentLocation(@RequestBody CatNameRequest catNameRequest) {
        return ResponseEntity.ok().body(catService.getRecentLocation(catNameRequest));
    }

    @PostMapping("/nearBy")
    ResponseEntity<CatListResponse> getCatListNearBy(@RequestBody SelfLocationRequest selfLocationRequest) {
        return ResponseEntity.ok().body(catService.getCatListNearBy(selfLocationRequest));
    }

    @PostMapping("/update")
    ResponseEntity<BooleanResponse> update(@RequestBody CatUpdateRequest catUpdateRequest) {
        return ResponseEntity.ok().body(new BooleanResponse(catService.update(catUpdateRequest)));
    }

    @PostMapping("/bookmark")
    ResponseEntity<BooleanResponse> favorite(@RequestBody BookmarkRequest bookmarkRequest) {
        return ResponseEntity.ok().body(new BooleanResponse(catService.addBookmark(bookmarkRequest)));
    }

    @GetMapping("/list")
    ResponseEntity<List<Cat>> list() {
        return ResponseEntity.ok().body(catService.list());
    }
}
