package com.assessment;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AppTests {
    public List<GrantProcessor.GrantEvent> events = new ArrayList<>();

    @BeforeEach
    void testSetup() {

        events.add(new GrantProcessor.GrantEvent("E1", "G100", 1, "PENDING"));
        events.add(new GrantProcessor.GrantEvent("E2", "G100", 5, "APPROVED"));
        events.add(new GrantProcessor.GrantEvent("E3", "G100", 3, "REJECTED"));
        events.add(new GrantProcessor.GrantEvent("E4", "G100", 5, "REJECTED"));
        events.add(new GrantProcessor.GrantEvent("E5", "G100", 6, "APPLIED"));
        events.add(new GrantProcessor.GrantEvent("E6", "G100", 2, "APPROVED"));
        events.add(new GrantProcessor.GrantEvent("E6", "G100", 2, "APPROVED"));
    }

    @Test
    void testDuplicateIds() {
        Map<String, String> result = GrantProcessor.latestStatus(events);
        assertEquals("E1", result.get("G100"));
    }

    @Test
    void testOutOfOrderEvents() {
        Map<String, String> result = GrantProcessor.latestStatus(events);
        assertNotEquals(3, result.get("E3"));

    }

    @Test
    void testReturnOnEqualSequence() {
        Map<String, String> result = GrantProcessor.latestStatus(events);
        assertEquals("REJECTED", result.get("G100"));
    }

}
