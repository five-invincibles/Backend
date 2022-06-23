package SWUNIV.Hackathon.service;

import SWUNIV.Hackathon.entity.Article;
import SWUNIV.Hackathon.entity.User;
import SWUNIV.Hackathon.entity.Vote;
import SWUNIV.Hackathon.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VoteService {
    private VoteRepository voteRepository;

    @Autowired
    public void setRepository(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    public boolean upvote(Article article, User user) {
        Optional<Vote> vote = voteRepository.findByUserAndArticle(user, article);
        if (vote.isEmpty()) {
            voteRepository.save(new Vote(user, article, false));
        } else {
            Vote v = vote.get();
            if (v.isUnvote()) {
                v.setUnvote(false);
            } else {
                return false;
            }
        }
        return true;
    }

    public boolean unvote(Article article, User user) {
        Optional<Vote> vote = voteRepository.findByUserAndArticle(user, article);
        if (vote.isPresent()) {
            Vote v = vote.get();
            if (!v.isUnvote()) {
                v.setUnvote(true);
                return true;
            }
        }
        return false;
    }
}
