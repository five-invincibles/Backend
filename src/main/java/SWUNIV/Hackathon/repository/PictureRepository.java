package SWUNIV.Hackathon.repository;

import SWUNIV.Hackathon.entity.DMS;
import SWUNIV.Hackathon.entity.Picture;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PictureRepository extends CrudRepository<Picture, Long> {
    @Query("select p from Picture p where " +
            "abs(p.longitude._1 * 60 + p.longitude._2 - ?3 * 60 + ?4) <= 1 " +
            "and abs(p.latitude._1 * 60 + p.latitude._2 - ?1 * 60 + ?2) <= 1")
    List<Picture> picturesIn2Minute(int latDeg, int latMin, int lonDeg, int lonMin);
}
