package SWUNIV.Hackathon.service;

import SWUNIV.Hackathon.data.CatDetails;
import SWUNIV.Hackathon.dto.BookmarkRequest;
import SWUNIV.Hackathon.dto.CatDetailsResponse;
import SWUNIV.Hackathon.dto.CatUpdateRequest;
import SWUNIV.Hackathon.dto.CatNameRequest;
import SWUNIV.Hackathon.dto.CatListResponse;
import SWUNIV.Hackathon.dto.CatRequest;
import SWUNIV.Hackathon.dto.LocationResponse;
import SWUNIV.Hackathon.dto.PictureRequest;
import SWUNIV.Hackathon.dto.SelfLocationRequest;
import SWUNIV.Hackathon.entity.Bookmark;
import SWUNIV.Hackathon.entity.Cat;
import SWUNIV.Hackathon.entity.DMS;
import SWUNIV.Hackathon.entity.User;
import SWUNIV.Hackathon.enumerations.CatAge;
import SWUNIV.Hackathon.enumerations.CatSex;
import SWUNIV.Hackathon.enumerations.CatSpecies;
import SWUNIV.Hackathon.repository.BookmarkRepository;
import SWUNIV.Hackathon.repository.CatRepository;
import SWUNIV.Hackathon.repository.UserRepository;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class CatService {

    private final CatRepository catRepository;

    private final UserRepository userRepository;

    private final PictureService pictureService;

    private final BookmarkRepository bookmarkRepository;

    public boolean register(MultipartFile file, CatRequest catRequest) {
        if (catRepository.existsByCatName(catRequest.getCatName()))
            return false;

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

    public CatDetailsResponse getDetails() {
        List<String> catDetails = new ArrayList<>();

        for (CatDetails catDetail : CatDetails.values()) {
            catDetails.add(catDetail.getValue());
        }

        return new CatDetailsResponse(catDetails);
    }

    public LocationResponse getRecentLocation(CatNameRequest catNameRequest) {

        final String catName = catNameRequest.getCatName();

        if (!catRepository.existsByCatName(catName))
            return null;

        final Cat cat = catRepository.findByCatName(catName);

        return new LocationResponse(cat.getLatitude(), cat.getLongitude());
    }

    public CatListResponse getCatListNearBy(SelfLocationRequest selfLocationRequest) {

        final DMS latitude = selfLocationRequest.getLatitude();
        final int latDeg = latitude.getDegree();
        final int latMin = latitude.getMinute();

        final DMS longitude = selfLocationRequest.getLongitude();
        final int lonDeg = longitude.getDegree();
        final int lonMin = longitude.getMinute();

        final List<Cat> catList = catRepository.findCatsIn2Minute(latDeg, latMin, lonDeg, lonMin);

        return new CatListResponse(catList);
    }

    public boolean update(CatUpdateRequest catUpdateRequest) {

        final String catName = catUpdateRequest.getCatName();

        final CatAge age = catUpdateRequest.getAge();

        final CatSex sex = catUpdateRequest.getSex();

        final List<String> details = catUpdateRequest.getDetails();

        final CatSpecies species = catUpdateRequest.getSpecies();

        final String kakaoID = catUpdateRequest.getKakaoID();

        if (!userRepository.existsByKakaoID(kakaoID))
            return false;

        final User modifier = userRepository.findUserByKakaoID(kakaoID);

        if (!catRepository.existsByCatName(catName))
            return false;

        Cat cat = catRepository.findByCatName(catName);

        if (age != null)
            cat.setAge(age);
        if (sex != null)
            cat.setSex(sex);
        if (!details.isEmpty())
            cat.setDetails(details);
        if (species != null)
            cat.setSpecies(species);

        cat.setModifier(modifier);
        cat.setLastModified(LocalDateTime.now());

        final Cat saved = catRepository.save(cat);

        return true;
    }

    public boolean bookmark(BookmarkRequest bookmarkRequest) {
        final String catName = bookmarkRequest.getCatName();

        final String kakaoID = bookmarkRequest.getKakaoID();

        if (!catRepository.existsByCatName(catName))
            return false;

        if (!userRepository.existsByKakaoID(kakaoID))
            return false;

        final Cat cat = catRepository.findByCatName(catName);

        final User user = userRepository.findUserByKakaoID(kakaoID);

        final Bookmark bookmark = Bookmark.builder()
            .cat(cat)
            .user(user)
            .build();

        bookmarkRepository.save(bookmark);

        return true;
    }
}
