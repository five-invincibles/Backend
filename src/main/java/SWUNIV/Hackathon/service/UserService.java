package SWUNIV.Hackathon.service;

import SWUNIV.Hackathon.dto.BooleanResponse;
import SWUNIV.Hackathon.dto.TokenRequest;
import SWUNIV.Hackathon.dto.SignUpRequest;
import SWUNIV.Hackathon.dto.UniversityResponse;
import SWUNIV.Hackathon.dto.UserResponse;
import SWUNIV.Hackathon.dto.UserUpdateRequest;
import SWUNIV.Hackathon.entity.Bookmark;
import SWUNIV.Hackathon.entity.Cat;
import SWUNIV.Hackathon.enumerations.Authority;
import SWUNIV.Hackathon.enumerations.University;
import SWUNIV.Hackathon.exception.InvalidKakaoTokenException;
import SWUNIV.Hackathon.repository.UserRepository;
import SWUNIV.Hackathon.entity.User;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;

    public JsonObject getUserDataFromKakaoToken(String token) throws InvalidKakaoTokenException {

        String reqURL = "https://kapi.kakao.com/v2/user/me";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);

        RestTemplate rt = new RestTemplate();
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
            new HttpEntity<>(null, headers);

        ResponseEntity<String> response = rt.exchange(
            reqURL,
            HttpMethod.GET,
            kakaoTokenRequest,
            String.class
        );

        String json = response.getBody();

        System.out.println("json = " + json);
        return JsonParser.parseString(json).getAsJsonObject();
    }

    @Transactional
    public BooleanResponse signUp(SignUpRequest signUpRequest) {

        final String accessToken = signUpRequest.getAccessToken();

        System.out.println("accessToken = " + accessToken);

        JsonObject jsonObject = getUserDataFromKakaoToken(accessToken);

        if (jsonObject.isJsonNull()) throw new InvalidKakaoTokenException();

        String id = jsonObject.get("id").getAsString();

        JsonObject kakaoAccount = jsonObject
            .get("kakao_account").getAsJsonObject();

        JsonObject kakaoProfile = kakaoAccount
            .get("profile").getAsJsonObject();

        boolean hasEmail = kakaoAccount
            .get("has_email").getAsBoolean();

        String email = "";

        if(hasEmail){
            email = kakaoAccount.get("email").getAsString();
        }

        if (userRepository.existsByEmail(email)) {
            return new BooleanResponse(false);
        }

        final User user = User.builder()
            .name(kakaoProfile.get("nickname").getAsString())
            .kakaoID(id)
            .accessToken(accessToken)
            .authority(Authority.GUEST)
            .picturePath(kakaoProfile.get("profile_image_url").getAsString())
            .university(University.valueOf(signUpRequest.getUniversity()))
            .email(email)
            .build();

        final User savedUser = userRepository.save(user);

        return new BooleanResponse(true);
    }

    public BooleanResponse logIn(TokenRequest tokenRequest) {

        final String kakaoToken = tokenRequest.getKakaoToken();

        JsonObject jsonObject = getUserDataFromKakaoToken(kakaoToken);

        if (jsonObject.isJsonNull()) throw new InvalidKakaoTokenException();

        String id = jsonObject.get("id").getAsString();

        JsonObject kakaoAccount = jsonObject
            .get("kakao_account").getAsJsonObject();

        boolean hasEmail = kakaoAccount
            .get("has_email").getAsBoolean();

        String email = "";

        if(hasEmail){
            email = kakaoAccount.get("email").getAsString();
        }

        if (!userRepository.existsByEmail(email)) return new BooleanResponse(false);

        User user = userRepository.findUserByEmail(email);

        if (!user.getKakaoID().equals(id)) {
            System.out.println("user.getUserID() = " + user.getKakaoID());
            System.out.println("id = " + id);
            return new BooleanResponse(false);
        }

        user.setAccessToken(kakaoToken);

        final User savedUser = userRepository.save(user);

        return new BooleanResponse(true);
    }

    public UniversityResponse getUniversities() {
        List<String> universities = new ArrayList<>();

        for (University university : University.values()) {
            universities.add(university.getValue());
        }

        return new UniversityResponse(universities);
    }

    public UserResponse me(TokenRequest tokenRequest) {

        final String kakaoToken = tokenRequest.getKakaoToken();

        JsonObject jsonObject = getUserDataFromKakaoToken(kakaoToken);

        if (jsonObject.isJsonNull()) throw new InvalidKakaoTokenException();

        String id = jsonObject.get("id").getAsString();

        User user = userRepository.findUserByKakaoID(id);

        final UserResponse userResponse = UserResponse.builder()
            .name(user.getName())
            .email(user.getEmail())
            .picturePath(user.getPicturePath())
            .university(user.getUniversity())
            .build();

        return userResponse;
    }

    public UserResponse update(UserUpdateRequest userUpdateRequest) {

        final String kakaoID = userUpdateRequest.getKakaoID();

        if (kakaoID == null) return null;

        if (!userRepository.existsByKakaoID(kakaoID)) {
            return null;
        }

        User user = userRepository.findUserByKakaoID(kakaoID);

        final String email = userUpdateRequest.getEmail();

        if (email != null) {
            user.setEmail(email);
        }

        final String name = userUpdateRequest.getName();

        if (name != null) {
            user.setName(name);
        }

        final University university = userUpdateRequest.getUniversity();

        if (university != null) {
            user.setUniversity(university);
        }

        final UserResponse userResponse = UserResponse.builder()
            .name(user.getName())
            .email(user.getEmail())
            .picturePath(user.getPicturePath())
            .university(user.getUniversity())
            .build();

        return userResponse;
    }

    public List<Cat> bookmarkedCats(String kakaoID) {
        User user = userRepository.findUserByKakaoID(kakaoID);
        return user.getBookmarks().stream()
                .map(Bookmark::getCat)
                .collect(Collectors.toList());
    }
}
