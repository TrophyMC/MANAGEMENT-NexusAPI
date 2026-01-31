package de.mecrytv.nexusapi.manager;

import de.mecrytv.databaseapi.DatabaseAPI;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class CooldownManager {

    private final String REDIS_PREFIX = "nexus:cooldown:";

    public void setCooldown(java.util.UUID uuid, String action, long time, TimeUnit unit) {
        String key = REDIS_PREFIX + action + ":" + uuid.toString();
        long seconds = unit.toSeconds(time);

        DatabaseAPI.getInstance().getRedis().setex(key, seconds, String.valueOf(System.currentTimeMillis() + unit.toMillis(time)));
    }

    public CompletableFuture<Long> getRemainingTime(java.util.UUID uuid, String action) {
        String key = REDIS_PREFIX + action + ":" + uuid.toString();

        return DatabaseAPI.getInstance().getRedis().get(key).thenApply(val -> {
            if (val == null) return 0L;
            long expireAt = Long.parseLong(val);
            long remaining = expireAt - System.currentTimeMillis();
            return Math.max(0L, remaining);
        });
    }

    public CompletableFuture<Boolean> hasCooldown(java.util.UUID uuid, String action) {
        return getRemainingTime(uuid, action).thenApply(time -> time > 0);
    }
}