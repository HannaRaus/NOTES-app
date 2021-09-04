package ua.goit.goitnotes.enums;

import org.junit.jupiter.api.Test;
import ua.goit.goitnotes.note.model.AccessType;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AccessTypeTest {

    @Test
    void byName() {
        assertEquals(AccessType.PUBLIC, AccessType.byName("PubLIC"));
        assertEquals(AccessType.PUBLIC, AccessType.byName("PUBLIC"));
        assertEquals(AccessType.PUBLIC, AccessType.byName("public"));
        assertEquals(AccessType.PRIVATE, AccessType.byName("private"));
        assertEquals(AccessType.PRIVATE, AccessType.byName("PRIVATE"));
        assertEquals(AccessType.PRIVATE, AccessType.byName("PRIVATE "));
        assertEquals(AccessType.PUBLIC, AccessType.byName(" PUBLIc "));
        assertEquals(AccessType.UNKNOWN, AccessType.byName("  "));
        assertEquals(AccessType.UNKNOWN, AccessType.byName(""));
        assertEquals(AccessType.UNKNOWN, AccessType.byName("skfuoiuo"));
        assertEquals(AccessType.UNKNOWN, AccessType.byName("7246^"));
    }
}