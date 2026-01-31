package de.mecrytv.nexusapi.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import de.mecrytv.databaseapi.DatabaseAPI;

public class GlobalNotifyer {

    private static final String REDIS_NOTIFY_CHANNEL = "nexus:global:notify";

    public void sendStaffMessage(String permission, String message) {
        JsonObject json = new JsonObject();
        json.addProperty("permission", permission);
        json.addProperty("message", message);
        json.addProperty("timestamp", System.currentTimeMillis());

        DatabaseAPI.getInstance().getRedis().publish(REDIS_NOTIFY_CHANNEL, json.toString()
        );
    }


    public void listen(java.util.function.Consumer<JsonObject> listener) {
        DatabaseAPI.getInstance().getRedis().subscribe(
                REDIS_NOTIFY_CHANNEL, msg -> {
                    JsonObject json = new Gson()
                            .fromJson(msg, JsonObject.class);
                    listener.accept(json);
                }
        );
    }
}