package com.assessment;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GrantProcessor {
    public record GrantEvent(
            String eventId,
            String grantId,
            long sequence,
            String status) {
    }

    public static Map<String, String> latestStatus(List<GrantEvent> events) {
        if (events == null || events.isEmpty()) {
            return new LinkedHashMap<>();
        }

        Map<String, String> result = new LinkedHashMap<>();
        // Track the highest sequence seen per grantId
        Map<String, Long> maxSequence = new LinkedHashMap<>();
        Set<String> seenEventIds = new HashSet<>();

        for (GrantEvent event : events) {
            // Ignore null events or events with null/blank fields or negative sequences
            if (event == null
                    || event.eventId() == null || event.eventId().isBlank()
                    || event.grantId() == null || event.grantId().isBlank()
                    || event.status() == null || event.status().isBlank()
                    || event.sequence() < 0) {
                continue;
            }

            // Ignore duplicate event IDs after their first valid occurrence
            if (seenEventIds.contains(event.eventId())) {
                continue;
            }
            seenEventIds.add(event.eventId());

            String grantId = event.grantId();
            long seq = event.sequence();

            if (!result.containsKey(grantId)) {
                // First valid event for this grant — record it
                result.put(grantId, event.status());
                maxSequence.put(grantId, seq);

            } else {
                // Only replace if this event has a strictly higher sequence number
                // (ties keep the earlier one, i.e. we do NOT replace on equal sequence)
                if (seq > maxSequence.get(grantId)) {
                    result.put(grantId, event.status());
                    maxSequence.put(grantId, seq);
                }
            }
        }

        return result;
    }
}
