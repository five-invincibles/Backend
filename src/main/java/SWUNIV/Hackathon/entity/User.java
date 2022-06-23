package SWUNIV.Hackathon.entity;

import SWUNIV.Hackathon.enumerations.Authority;
import SWUNIV.Hackathon.enumerations.University;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Collection;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
public class User extends BaseEntity{

    @Setter
    private String name;

    private String kakaoID;

    @Setter
    private String accessToken;

    @Enumerated(EnumType.STRING)
    private Authority authority = Authority.USER;

    private String picturePath;

    @Setter
    @Enumerated(EnumType.STRING)
    private University university;

    @Setter
    private String email;

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Collection<Picture> pictures;
    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Collection<Article> articles;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Collection<Bookmark> bookmarks;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Collection<Vote> votes;
}
