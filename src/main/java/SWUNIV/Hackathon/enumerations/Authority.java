package SWUNIV.Hackathon.enumerations;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Authority {
    GUEST("Guest"), USER("USER"), ADMIN("ADMIN");

    private final String value;
}