package SWUNIV.Hackathon.entity;

import SWUNIV.Hackathon.enumerations.CatAge;
import SWUNIV.Hackathon.enumerations.CatSpecies;
import SWUNIV.Hackathon.enumerations.CatSex;
import java.util.ArrayList;
import java.util.List;
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
@Setter
public class Cat extends BaseEntity {

    private String catName;

    @Enumerated(EnumType.STRING)
    private CatSpecies species;

    @Enumerated(EnumType.STRING)
    private CatAge age;

    @Enumerated(EnumType.STRING)
    private CatSex sex;

    private String thumbnailKey;

    private LocalDateTime lastModified;

    @PrePersist
    void preInsert() {
        if (this.lastModified == null)
            this.lastModified = LocalDateTime.now();
    }
  
    @ElementCollection
    private List<String> details = new ArrayList<>();

    @Embedded
    @AttributeOverride(name= "degree", column = @Column(name = "latitude_1"))
    @AttributeOverride(name= "minute", column = @Column(name = "latitude_2"))
    @AttributeOverride(name= "second", column = @Column(name = "latitude_3"))
    @Setter private DMS latitude;

    @Embedded
    @AttributeOverride(name= "degree", column = @Column(name = "longitude_1"))
    @AttributeOverride(name= "minute", column = @Column(name = "longitude_2"))
    @AttributeOverride(name= "second", column = @Column(name = "longitude_3"))
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
