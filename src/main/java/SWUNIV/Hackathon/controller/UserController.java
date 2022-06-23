package SWUNIV.Hackathon.controller;

import SWUNIV.Hackathon.dto.BooleanResponse;
import SWUNIV.Hackathon.dto.TokenRequest;
import SWUNIV.Hackathon.dto.SignUpRequest;
import SWUNIV.Hackathon.dto.UniversityResponse;
import SWUNIV.Hackathon.dto.UserResponse;
import SWUNIV.Hackathon.dto.UserUpdateRequest;
import SWUNIV.Hackathon.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<BooleanResponse> signUp(@RequestBody SignUpRequest signUpRequest) {
        return ResponseEntity.ok().body(userService.signUp(signUpRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<BooleanResponse> logIn(@RequestBody TokenRequest tokenRequest) {
        return ResponseEntity.ok().body(userService.logIn(tokenRequest));
    }

    @GetMapping("/getUniversities")
    public ResponseEntity<UniversityResponse> getUniversities() {
        return ResponseEntity.ok().body(userService.getUniversities());
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> me(@RequestBody TokenRequest tokenRequest) {
        return ResponseEntity.ok().body(userService.me(tokenRequest));
    }

    @PostMapping("/update")
    public ResponseEntity<UserResponse> update(@RequestBody UserUpdateRequest userUpdateRequest) {
        return ResponseEntity.ok().body(userService.update(userUpdateRequest));
    }
}
