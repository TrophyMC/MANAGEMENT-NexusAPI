package de.mecrytv.nexusapi.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import de.mecrytv.databaseapi.DatabaseAPI;
import de.mecrytv.languageapi.LanguageAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

import java.util.function.BiConsumer;

public class GlobalNotifyer {

    private static final String REDIS_NOTIFY_CHANNEL = "nexus:global:notify";
    private final Gson gson = new Gson();

    public void sendStaffNotification(String permission, String langKey, String... replacements) {
        if (DatabaseAPI.getInstance() == null || DatabaseAPI.getInstance().getRedis() == null) return;

        JsonObject json = new JsonObject();
        json.addProperty("permission", permission);
        json.addProperty("langKey", langKey);

        if (replacements != null && replacements.length > 0) {
            json.addProperty("replacements", String.join(";;", replacements));
        }

        DatabaseAPI.getInstance().getRedis().publish(REDIS_NOTIFY_CHANNEL, json.toString());
    }

    public void listen(LanguageAPI languageAPI, BiConsumer<String, Component> callback) {
        if (DatabaseAPI.getInstance() == null || DatabaseAPI.getInstance().getRedis() == null) return;

        DatabaseAPI.getInstance().getRedis().subscribe(REDIS_NOTIFY_CHANNEL, msg -> {
            try {
                JsonObject json = gson.fromJson(msg, JsonObject.class);
                String permission = json.get("permission").getAsString();
                String langKey = json.get("langKey").getAsString();
                String[] reps = json.has("replacements") ? json.get("replacements").getAsString().split(";;") : new String[0];

                String rawMessage = languageAPI.getTranslation("en_US", langKey);

                if (rawMessage != null && !rawMessage.isEmpty()) {
                    for (int i = 0; i < reps.length; i += 2) {
                        if (i + 1 < reps.length) {
                            rawMessage = rawMessage.replace(reps[i], reps[i + 1]);
                        }
                    }
                    callback.accept(permission, MiniMessage.miniMessage().deserialize(rawMessage));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}