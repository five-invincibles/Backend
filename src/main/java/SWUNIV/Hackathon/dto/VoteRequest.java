package SWUNIV.Hackathon.dto;

import lombok.Getter;

@Getter
public class VoteRequest {
    // "UP"/"DOWN"
    private String voteType;

    private Long articleId;
    private String kakaoID;
}
