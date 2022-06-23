package SWUNIV.Hackathon.entity;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
public class Cat extends BaseEntity {
    private String catName;
    private String species;
    private Long age;
    private String sex;
    private LocalDateTime lastModified;

    @PrePersist
    void preInsert() {
        if (this.lastModified == null)
            this.lastModified = LocalDateTime.now();
    }

    @Embedded
    @AttributeOverride(name="_1", column = @Column(name = "latitude_1"))
    @AttributeOverride(name="_2", column = @Column(name = "latitude_2"))
    @AttributeOverride(name="_3", column = @Column(name = "latitude_3"))
    @Setter private DMS latitude;

    @Embedded
    @AttributeOverride(name="_1", column = @Column(name = "longitude_1"))
    @AttributeOverride(name="_2", column = @Column(name = "longitude_2"))
    @AttributeOverride(name="_3", column = @Column(name = "longitude_3"))
    @Setter private DMS longitude;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User modifier;

    @OneToMany(mappedBy = "cat", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Collection<Picture> pictures;
    @OneToMany(mappedBy = "cat", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Collection<Article> articles;

    public double distance(DMS lat, DMS lon) {
        DMS difflat = latitude.diffAbs(lat);
        DMS difflon = longitude.diffAbs(lon);
        return Math.sqrt(difflat.latitudeToMeterIn35Degree()
                + difflon.longitudeToMeterIn35Degree());
    }
}
