package SWUNIV.Hackathon.controller;

import SWUNIV.Hackathon.dto.BooleanResponse;
import SWUNIV.Hackathon.dto.PictureRequest;
import SWUNIV.Hackathon.service.PictureService;
import com.jlefebure.spring.boot.minio.MinioException;
import com.jlefebure.spring.boot.minio.MinioService;
import io.minio.messages.Item;
import java.io.UnsupportedEncodingException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/picture")
public class PictureController {

    @Autowired
    private final MinioService minioService;
    private final PictureService pictureService;

    @GetMapping("/list")
    public List<Item> photoList() throws MinioException {
        return minioService.list();
    }

    @PostMapping(value = "/save", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<BooleanResponse> save(@RequestPart("file") MultipartFile file, @RequestPart PictureRequest pictureRequest) {

        BooleanResponse booleanResponse;

        try {
            booleanResponse = new BooleanResponse (pictureService.save(file, pictureRequest));
        } catch (UnsupportedEncodingException uee) {
            uee.printStackTrace();
            booleanResponse = new BooleanResponse (false);
        }

        return ResponseEntity.ok().body(booleanResponse);
    }
}

