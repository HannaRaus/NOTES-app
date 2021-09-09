package ua.goit.goitnotes.note.model;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import ua.goit.goitnotes.error_handling.InvalidAccessTypeException;

import java.util.Arrays;

@Slf4j
public enum AccessType {
    PRIVATE,
    PUBLIC;

    public static boolean isAccessType(@NonNull String accessTypeName){
        log.info("isAccessType .");
        return Arrays.stream(values())
                .anyMatch(accessType -> accessType.name().equalsIgnoreCase(accessTypeName.strip()));
    }

    public static AccessType byName(@NonNull String accessTypeName){
        log.info("byName .");
        if(isAccessType(accessTypeName)) {
            return valueOf(accessTypeName.strip().toUpperCase());
        }else{
            log.error("byName . There is no such access type for notes");
            throw new InvalidAccessTypeException("There is no such access type for notes");
        }
    }
}
