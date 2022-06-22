package SWUNIV.Hackathon.repository;

import SWUNIV.Hackathon.entity.Cat;
import SWUNIV.Hackathon.entity.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CatRepository extends JpaRepository<Cat, Long>, GeometricCatRepository {
    /**
     * 주어진 위경도로부터 위도 2분, 경도 2분 직경의 직사각형 범위 내 서식하는 것으로 추정되는 고양이들의 목록
     * @param latDeg 위도(도)
     * @param latMin 위도(분)
     * @param lonDeg 경도(도)
     * @param lonMin 경도(분)
     * @return 고양이들의 목록
     */
    @Query("select c from Cat c where " +
            "abs(c.longitude._1 * 60 + c.longitude._2 - ?3 * 60 + ?4) <= 1 " +
            "and abs(c.latitude._1 * 60 + c.latitude._2 - ?1 * 60 + ?2) <= 1")
    List<Cat> findCatsIn2Minute(int latDeg, int latMin, int lonDeg, int lonMin);

    @Query("select p from Picture p inner join p.cat c on c.id = :cat_id " +
            "where p.uploadedDate > :start")
    List<Picture> findRecentPictureOfCat(@Param("cat_id") Long cat_id, @Param("start") LocalDateTime startDate);

}
