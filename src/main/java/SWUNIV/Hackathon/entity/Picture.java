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
    private String filename;
    private String title;
    private LocalDateTime uploadedDate;

    @Embedded
    @AttributeOverride(name="_1", column = @Column(name = "latitude_1"))
    @AttributeOverride(name="_2", column = @Column(name = "latitude_2"))
    @AttributeOverride(name="_3", column = @Column(name = "latitude_3"))
    private DMS latitude;

    @Embedded
    @AttributeOverride(name="_1", column = @Column(name = "longitude_1"))
    @AttributeOverride(name="_2", column = @Column(name = "longitude_2"))
    @AttributeOverride(name="_3", column = @Column(name = "longitude_3"))
    private DMS longitude;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cat_id")
    private Cat cat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User author;
}
