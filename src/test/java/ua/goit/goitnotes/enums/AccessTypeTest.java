package ua.goit.goitnotes.enums;

import org.junit.jupiter.api.Test;
import ua.goit.goitnotes.error_handling.InvalidAccessTypeException;
import ua.goit.goitnotes.note.model.AccessType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        assertThrows(InvalidAccessTypeException.class, () -> AccessType.byName("  "));
        assertThrows(InvalidAccessTypeException.class, () -> AccessType.byName(""));
        assertThrows(InvalidAccessTypeException.class, () -> AccessType.byName("skfuoiuo"));
        assertThrows(InvalidAccessTypeException.class, () -> AccessType.byName("7246^"));
    }

    @Test
    public void isAccessType(){
        assertTrue(AccessType.isAccessType("PubLIC"));
        assertTrue(AccessType.isAccessType("PUBLIC"));
        assertTrue(AccessType.isAccessType("public"));
        assertTrue(AccessType.isAccessType("private"));
        assertTrue(AccessType.isAccessType("PRIVATE"));
        assertTrue(AccessType.isAccessType("PRIVATE "));
        assertTrue(AccessType.isAccessType(" PUBLIc "));
        assertFalse(AccessType.isAccessType("  "));
        assertFalse(AccessType.isAccessType(""));
        assertFalse(AccessType.isAccessType("skfuoiuo"));
        assertFalse(AccessType.isAccessType("7246^"));
    }
}