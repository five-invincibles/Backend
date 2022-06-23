package SWUNIV.Hackathon.controller;

import SWUNIV.Hackathon.dto.BooleanResponse;
import SWUNIV.Hackathon.dto.PictureRequest;
import SWUNIV.Hackathon.service.PictureService;
import com.google.api.client.util.IOUtils;
import com.jlefebure.spring.boot.minio.MinioException;
import com.jlefebure.spring.boot.minio.MinioService;
import io.minio.messages.Item;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/picture")
public class PictureController {

    private MinioService minioService;
    private PictureService pictureService;

    @GetMapping("/list")
    public List<Item> photoList() throws MinioException {
        return minioService.list();
    }

    @PostMapping("/save")
    public ResponseEntity<BooleanResponse> save(@RequestParam("file") MultipartFile file, @RequestBody PictureRequest pictureRequest) {

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

