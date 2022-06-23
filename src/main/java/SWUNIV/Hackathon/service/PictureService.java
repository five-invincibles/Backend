package SWUNIV.Hackathon.service;

import SWUNIV.Hackathon.dto.BooleanResponse;
import SWUNIV.Hackathon.dto.PictureRequest;
import SWUNIV.Hackathon.entity.Cat;
import SWUNIV.Hackathon.entity.DMS;
import SWUNIV.Hackathon.entity.Picture;
import SWUNIV.Hackathon.repository.CatRepository;
import SWUNIV.Hackathon.repository.PictureRepository;
import SWUNIV.Hackathon.repository.UserRepository;
import com.google.api.client.util.IOUtils;
import com.jlefebure.spring.boot.minio.MinioException;
import com.jlefebure.spring.boot.minio.MinioService;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class PictureService {

    @Autowired
    private MinioService minioService;

    private final PictureRepository pictureRepository;

    private final CatRepository catRepository;

    private final UserRepository userRepository;

    public Boolean save(MultipartFile file, PictureRequest pictureRequest)
        throws UnsupportedEncodingException {

        final String pictureKey = upload(file);

        final Long catID = pictureRequest.getCatID();

        final String userKakaoID = pictureRequest.getKakaoID();

        if (!catRepository.existsById(catID)) {
            System.out.println("cat id");
            return false;
        }

        if (!userRepository.existsByKakaoID(userKakaoID)) {
            System.out.println("kakao id");
            return false;
        }

        final Picture picture = Picture.builder()
            .description(pictureRequest.getDescription())
            .key(pictureKey)
            .title(pictureRequest.getTitle())
            .uploadedDate(LocalDateTime.now())
            .latitude(pictureRequest.getLatitude())
            .longitude(pictureRequest.getLongitude())
            .cat(catRepository.getById(catID))
            .author(userRepository.findUserByKakaoID(userKakaoID))
            .build();

        Picture saved = pictureRepository.save(picture);

        return true;
    }

    public String upload(MultipartFile file) {

        String filename = file.getOriginalFilename();

        final String id = UUID.randomUUID().toString();

        Path path = Path.of(filename);

        try {
            minioService.upload(path, file.getInputStream(), file.getContentType());
        } catch (MinioException e) {
            throw new IllegalStateException("The file cannot be upload on the internal storage. Please retry later", e);
        } catch (IOException e) {
            throw new IllegalStateException("The file cannot be read", e);
        }

        return id;
    }

    public void getPhoto(String object) throws MinioException, IOException {
        InputStream inputStream = minioService.get(Path.of(object));
        InputStreamResource inputStreamResource = new InputStreamResource(inputStream);

        File file = inputStreamResource.getFile();

        file.createNewFile();
    }
}
