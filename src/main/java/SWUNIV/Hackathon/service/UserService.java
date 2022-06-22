package SWUNIV.Hackathon.service;

import SWUNIV.Hackathon.dto.EmailRequest;
import SWUNIV.Hackathon.dto.LogInRequest;
import SWUNIV.Hackathon.dto.SignUpRequest;
import SWUNIV.Hackathon.dto.StringResponse;
import SWUNIV.Hackathon.dto.UserMapper;
import SWUNIV.Hackathon.dto.UserResponse;
import SWUNIV.Hackathon.exception.DuplicateEmailException;
import SWUNIV.Hackathon.exception.DuplicateNicknameException;
import SWUNIV.Hackathon.exception.InvalidPasswordException;
import SWUNIV.Hackathon.exception.InvalidUserException;
import SWUNIV.Hackathon.repository.UserRepository;
import SWUNIV.Hackathon.util.JwtUtil;
import SWUNIV.Hackathon.auth.TokenInfo;
import SWUNIV.Hackathon.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class UserService {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;

    public StringResponse checkEmailAvailability(EmailRequest emailRequest) {
        if (userRepository.existsByEmail(emailRequest.getEmail())) throw new DuplicateEmailException();

        return new StringResponse("사용가능한 이메일입니다");
    }

    @Transactional
    public UserResponse signUp(SignUpRequest signUpRequest) {

        if (userRepository.existsByEmail(signUpRequest.getEmail())) throw new DuplicateEmailException(); // 혹시 모름
        if (userRepository.existsByNickname(signUpRequest.getNickname())) throw new DuplicateNicknameException();

        signUpRequest.setPassword(authService.encodePassword(signUpRequest.getPassword()));

        final User user = UserMapper.INSTANCE.requestToUser(signUpRequest);
        final User savedUser = userRepository.save(user);

        UserResponse userResponse = UserMapper.INSTANCE.userToResponse(savedUser);
        userResponse.setTokenResponse(jwtUtil.generateToken(getTokenInfo(savedUser)));

        return userResponse;
    }

    public UserResponse logIn(LogInRequest logInRequest) {

        if (!userRepository.existsByEmail(logInRequest.getEmail())) throw new InvalidUserException();

        final User user = userRepository.findUserByEmail(logInRequest.getEmail());

        if (!passwordEncoder.matches(logInRequest.getPassword(), user.getPassword())) throw new InvalidPasswordException();

        UserResponse userResponse = UserMapper.INSTANCE.userToResponse(user);
        userResponse.setTokenResponse(jwtUtil.generateToken(getTokenInfo(user)));

        return userResponse;
    }

    private TokenInfo getTokenInfo(User user) { // 이거 JwtUtil로 돌릴지
        return new TokenInfo(user.getId(), user.getEmail(), user.getAuthority());
    }
}
