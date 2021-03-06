package SWUNIV.Hackathon.service;

import SWUNIV.Hackathon.dto.PictureRequest;
import SWUNIV.Hackathon.dto.PictureResponse;
import SWUNIV.Hackathon.entity.Cat;
import SWUNIV.Hackathon.entity.Picture;
import SWUNIV.Hackathon.repository.CatRepository;
import SWUNIV.Hackathon.repository.PictureRepository;
import SWUNIV.Hackathon.repository.UserRepository;
import com.jlefebure.spring.boot.minio.MinioException;
import com.jlefebure.spring.boot.minio.MinioService;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    public PictureResponse save(MultipartFile file, PictureRequest pictureRequest)
        throws UnsupportedEncodingException {

        final String pictureKey = upload(file);

        final Long catID = pictureRequest.getCatID();

        final String userKakaoID = pictureRequest.getKakaoID();

        if (!catRepository.existsById(catID)) {
            return null;
        }

        if (!userRepository.existsByKakaoID(userKakaoID)) {
            System.out.println("kakao id");
            return null;
        }

        System.out.println("data : " + pictureRequest.getDate());

        LocalDate date = LocalDate.parse(pictureRequest.getDate(),
            DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        System.out.println("date = " + date);

        final Cat cat = catRepository.getById(catID);

        final Picture picture = Picture.builder()
            .pictureKey(pictureKey)
            .uploadedDate(LocalDateTime.now())
            .picturedDate(date)
            .latitude(pictureRequest.getLatitude())
            .longitude(pictureRequest.getLongitude())
            .cat(cat)
            .author(userRepository.findUserByKakaoID(userKakaoID))
            .build();

        Picture saved = pictureRepository.save(picture);

        catRepository.updateHabitat(cat);

        return new PictureResponse(saved.getId(), saved.getPictureKey());
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
