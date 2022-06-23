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
    private String pictureKey;

    private LocalDateTime uploadedDate;

    private LocalDateTime picturedDate;

    @PrePersist
    void preInsert() {
        if (this.uploadedDate == null)
            this.uploadedDate = LocalDateTime.now();
    }

    @Embedded
    @AttributeOverride(name= "degree", column = @Column(name = "latitude_1"))
    @AttributeOverride(name= "minute", column = @Column(name = "latitude_2"))
    @AttributeOverride(name= "second", column = @Column(name = "latitude_3"))
    private DMS latitude;

    @Embedded
    @AttributeOverride(name= "degree", column = @Column(name = "longitude_1"))
    @AttributeOverride(name= "minute", column = @Column(name = "longitude_2"))
    @AttributeOverride(name= "second", column = @Column(name = "longitude_3"))
    private DMS longitude;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cat_id")
    private Cat cat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private Article article;
}
