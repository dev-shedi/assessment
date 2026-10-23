package com.assessment;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class App {
    public static void main(String[] args) {
        List<GrantProcessor.GrantEvent> events = new ArrayList<>();
        events.add(new GrantProcessor.GrantEvent("E1", "G100", 1, "PENDING"));
        events.add(new GrantProcessor.GrantEvent("E2", "G100", 5, "APPROVED"));
        events.add(new GrantProcessor.GrantEvent("E3", "G100", 3, "REJECTED"));
        events.add(new GrantProcessor.GrantEvent("E4", "G100", 5, "REJECTED"));
        events.add(new GrantProcessor.GrantEvent("E5", "G100", 6, "APPLIED"));
        events.add(new GrantProcessor.GrantEvent("E6", "G100", 2, "APPROVED"));
        System.out.println("Printing the Latest Grant Events Status: ");
        System.out.println(GrantProcessor.latestStatus(events));
    }


}
