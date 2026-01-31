package de.mecrytv.nexusapi.utils;

import de.mecrytv.nexusapi.NexusAPI;

import java.util.*;
import java.util.concurrent.CompletableFuture;

public class HistoryHelper {

    private record HistoryEntry(String type, String reason, long timestamp, String staff, long expiry, String reportId) {}

    public CompletableFuture<List<HistoryEntry>> getFullHistory(UUID uuid) {
        var repo = NexusAPI.getInstance().getRepoManager();
        String id = uuid.toString();

        return CompletableFuture.allOf(
                repo.getBanRepository().getList("targetUUID", id),
                repo.getMuteRepository().getList("targetUUID", id),
                repo.getWarnRepository().getList("targetUUID", id)
        ).thenApply(v -> {
            List<HistoryEntry> history = new ArrayList<>();

            repo.getBanRepository().getList("targetUUID", id).join().forEach(b ->
                    history.add(new HistoryEntry("BAN", b.getReason(), b.getBanTimestamp(), b.getStaffName(), b.getBanExpires(), b.getReportID())));

            repo.getMuteRepository().getList("targetUUID", id).join().forEach(m ->
                    history.add(new HistoryEntry("MUTE", m.getReason(), m.getMuteTimestamp(), m.getStaffName(), m.getMuteExpires(), m.getReportID())));

            repo.getWarnRepository().getList("targetUUID", id).join().forEach(w ->
                    history.add(new HistoryEntry("WARN", w.getReason(), w.getWarnTimestamp(), w.getStaffName(), -1L, w.getReportID())));

            history.sort(Comparator.comparingLong(HistoryEntry::timestamp).reversed());
            return history;
        });
    }
}