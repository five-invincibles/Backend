package SWUNIV.Hackathon.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
public class Picture extends BaseEntity {
    private String description;

    private String path;
    private String title;
    private LocalDateTime uploadedDate;
    private Double uploadedLatitude;
    private Double uploadedLongitude;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cat_id")
    private Cat cat;

    public double distance(double currentLatitude, double currentLongitude) {
        double diffLatitude = currentLatitude - uploadedLatitude;
        double diffLongitude = currentLongitude - uploadedLongitude;
        double squareLatitude = diffLatitude*diffLatitude;
        double squareLongitude = diffLongitude*diffLongitude;
        return Math.sqrt(squareLatitude + squareLongitude);
    }
}
