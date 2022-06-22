package SWUNIV.Hackathon.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalTime;

@Getter
@AllArgsConstructor
public class ErrorResponse {

    private LocalTime timeStamp;
    private String message;
    private int status;
    private String code;
}
