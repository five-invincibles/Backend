package SWUNIV.Hackathon.repository;

import SWUNIV.Hackathon.entity.Cat;
import SWUNIV.Hackathon.entity.DMS;
import SWUNIV.Hackathon.entity.Picture;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
public class GeometricCatRepositoryImpl implements GeometricCatRepository {
    @Autowired
    @Lazy
    CatRepository catRepository;

    @Override
    public void updateHabitat(Cat cat) {
        List<Picture> recentPics = catRepository.findRecentPictureOfCat(cat.getId(), LocalDateTime.now().minusWeeks(4));
        long latitudeDegreeSum = 0, longitudeDegreeSum = 0;
        long latitudeMinuteSum = 0, longitudeMinuteSum = 0;
        double latitudeSecondSum = 0., longitudeSecondSum = 0.;
        int n = recentPics.size();
        for (Picture p : recentPics) {
            DMS latitude = p.getLatitude();
            DMS longitude = p.getLongitude();
            latitudeDegreeSum += latitude.getDegree();
            longitudeDegreeSum += longitude.getDegree();
            latitudeMinuteSum += latitude.getMinute();
            longitudeMinuteSum += longitude.getMinute();
            latitudeSecondSum += latitude.getSecond();
            longitudeSecondSum += longitude.getSecond();
        }
        cat.setLatitude(new DMS((int)(latitudeDegreeSum/n), (int)(latitudeMinuteSum/n), latitudeSecondSum/n));
        cat.setLongitude(new DMS((int)(longitudeDegreeSum/n), (int)(longitudeMinuteSum/n), longitudeSecondSum/n));
        catRepository.save(cat);
    }
}
