import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
public class App {
    public static void main(String[] args) {
        System.out.println("Hello World");

        List<App.GrantProcessor.GrantEvent> events = new ArrayList<>();
        events.add(new App.GrantProcessor.GrantEvent("E1", "G1", 1, "APPLIED"));
        events.add(new App.GrantProcessor.GrantEvent("E2", "G2", 1, "APPLIED"));
        events.add(new App.GrantProcessor.GrantEvent("E3", "G1", 2, "APPROVED"));
        events.add(new App.GrantProcessor.GrantEvent("E4", "G3", 1, "REJECTED"));
        events.add(new App.GrantProcessor.GrantEvent("E5", "G2", 3, "IN_REVIEW"));
        events.add(new App.GrantProcessor.GrantEvent("E6", "G1", 1, "APPLIED"));
        events.add(new App.GrantProcessor.GrantEvent("E7", "G2", 2, "APPROVED"));

        System.out.println(latestStatus(events));

    }

    public class GrantProcessor {
    record GrantEvent(
        String eventId,
        String grantId,
        long sequence,
        String status
    ) {}

    public static Map<String, String> latestStatus(
            List<GrantEvent> events) {
        // TODO: Implement
        if (events == null || events.isEmpty()) {
            return new LinkedHashMap<>();
        }

        Map<String, String> result = new LinkedHashMap<>();
        List<GrantEvent> validEvents = new ArrayList<>();
        Set<String> seenEventIds = new HashSet<>();

        for (GrantEvent event : events) {
            if (event == null || event.eventId() == null || event.grantId() == null || event.status() == null || event.sequence() < 0) {
                continue;
            }
            if (seenEventIds.contains(event.eventId())) {
                continue;
            }
            seenEventIds.add(event.eventId());
            validEvents.add(event);
        }
        for (GrantEvent event : validEvents) {
            if (!result.containsKey(event.grantId())) {
                result.put(event.grantId(), event.status());
            } else {

               if (event.sequence() > validEvents.stream().filter(e -> e.grantId().equals(event.grantId())).findFirst().get().sequence()) {
                result.remove(event.grantId());
                result.put(event.grantId(), event.status());
               }
            }
        }
        return result;
    }
}
}
