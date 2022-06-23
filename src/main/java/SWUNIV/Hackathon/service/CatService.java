package SWUNIV.Hackathon.service;

import SWUNIV.Hackathon.dto.CatRequest;
import SWUNIV.Hackathon.dto.PictureRequest;
import SWUNIV.Hackathon.entity.Cat;
import SWUNIV.Hackathon.entity.DMS;
import SWUNIV.Hackathon.entity.User;
import SWUNIV.Hackathon.repository.CatRepository;
import SWUNIV.Hackathon.repository.UserRepository;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import jdk.swing.interop.SwingInterOpUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class CatService {

    private final CatRepository catRepository;

    private final UserRepository userRepository;

    private final PictureService pictureService;

    public boolean register(MultipartFile file, CatRequest catRequest) {
        if (catRepository.existsByCatName(catRequest.getCatName())) return false;

        final PictureRequest pictureRequest = catRequest.getPictureRequest();

        final DMS latitude = pictureRequest.getLatitude();

        final DMS longitude = pictureRequest.getLongitude();

        if (!userRepository.existsByKakaoID(pictureRequest.getKakaoID())) {
            return false;
        }

        final User modifier = userRepository.findUserByKakaoID(pictureRequest.getKakaoID());

        final Cat cat = Cat.builder()
            .catName(catRequest.getCatName())
            .species(catRequest.getSpecies())
            .age(catRequest.getAge())
            .sex(catRequest.getSex())
            .lastModified(LocalDateTime.now())
            .details(catRequest.getDetails())
            .latitude(latitude)
            .longitude(longitude)
            .modifier(modifier)
            .build();

        Cat savedCat = catRepository.save(cat);

        pictureRequest.setCatID(savedCat.getId());

        try {
            Boolean isSaved = pictureService.save(file, pictureRequest);
        } catch (UnsupportedEncodingException uee) {
            uee.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
