package ua.goit.goitnotes.model.entity.Enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

public enum AccessType {
    PRIVATE("PRIVATE"),
    PUBLIC("PUBLIC");

    @Getter
    private final String accessType;

    AccessType(String accessType) {
        this.accessType = accessType;
    }

    public static Optional<AccessType> getAccess(String accessType) {
        return Arrays.stream(AccessType.values())
                .filter(value -> value.getAccessType().equals(accessType))
                .findAny();
    }
}
