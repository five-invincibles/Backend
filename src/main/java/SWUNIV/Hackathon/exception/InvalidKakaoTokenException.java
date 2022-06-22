package SWUNIV.Hackathon.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class InvalidKakaoTokenException extends BaseException {
    private final static String message = "잘못된 토큰입니다.";

    private final static HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

    public InvalidKakaoTokenException() {
        super(message, httpStatus);
    }
}
