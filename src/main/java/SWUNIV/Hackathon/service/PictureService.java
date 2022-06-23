package SWUNIV.Hackathon.service;

import SWUNIV.Hackathon.dto.BooleanResponse;
import SWUNIV.Hackathon.dto.PictureRequest;
import SWUNIV.Hackathon.entity.Cat;
import SWUNIV.Hackathon.entity.DMS;
import SWUNIV.Hackathon.entity.Picture;
import SWUNIV.Hackathon.repository.CatRepository;
import SWUNIV.Hackathon.repository.PictureRepository;
import com.google.api.client.util.IOUtils;
import com.jlefebure.spring.boot.minio.MinioException;
import com.jlefebure.spring.boot.minio.MinioService;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.file.Path;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class PictureService {

    @Autowired
    private MinioService minioService;

    private PictureRepository pictureRepository;

    private CatRepository catRepository;

    public Boolean save(MultipartFile file, PictureRequest pictureRequest)
        throws UnsupportedEncodingException {

        String filename = file.getOriginalFilename();

        upload(file);

        filename = URLEncoder.encode(filename, "UTF-8");

        final Long catID = pictureRequest.getCatID();

        if (!catRepository.existsById(catID)) {
            return false;
        }

        final Picture picture = Picture.builder()
            .description(pictureRequest.getDescription())
            .filename(filename)
            .title(pictureRequest.getTitle())
            .uploadedDate(pictureRequest.getUploadedDate())
            .latitude(pictureRequest.getLatitude())
            .longitude(pictureRequest.getLongitude())
            .cat(catRepository.getById(catID))
            .build();

        Picture saved = pictureRepository.save(picture);

        return true;
    }

    public void upload(MultipartFile file) {

        String filename = file.getOriginalFilename();

        Path path = Path.of(filename);
        try {
            minioService.upload(path, file.getInputStream(), file.getContentType());
        } catch (MinioException e) {
            throw new IllegalStateException("The file cannot be upload on the internal storage. Please retry later", e);
        } catch (IOException e) {
            throw new IllegalStateException("The file cannot be read", e);
        }
    }

    public void getPhoto(String object) throws MinioException, IOException {
        InputStream inputStream = minioService.get(Path.of(object));
        InputStreamResource inputStreamResource = new InputStreamResource(inputStream);

        File file = inputStreamResource.getFile();

        file.createNewFile();
    }
}
