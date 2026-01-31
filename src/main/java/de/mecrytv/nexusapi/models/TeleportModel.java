package de.mecrytv.nexusapi.models;

import com.google.gson.JsonObject;
import de.mecrytv.databaseapi.model.ICacheModel;

public class TeleportModel implements ICacheModel {

    private String staffUUID;
    private String targetUUID;
    private String targetName;

    public TeleportModel() {}

    public TeleportModel(String staffUUID, String targetUUID, String targetName) {
        this.staffUUID = staffUUID;
        this.targetUUID = targetUUID;
        this.targetName = targetName;
    }

    @Override
    public String getIdentifier() {
        return staffUUID;
    }

    @Override
    public JsonObject serialize() {
        JsonObject json = new JsonObject();
        json.addProperty("staffUUID", staffUUID);
        json.addProperty("targetUUID", targetUUID);
        json.addProperty("targetName", targetName);
        return json;
    }

    @Override
    public void deserialize(JsonObject data) {
        this.staffUUID = data.get("staffUUID").getAsString();
        this.targetUUID = data.get("targetUUID").getAsString();
        this.targetName = data.get("targetName").getAsString();
    }

    public String getStaffUUID() {
        return staffUUID;
    }
    public void setStaffUUID(String staffUUID) {
        this.staffUUID = staffUUID;
    }
    public void setTargetUUID(String targetUUID) {
        this.targetUUID = targetUUID;
    }
    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }
    public String getTargetUUID() {
        return targetUUID;
    }
    public String getTargetName() {
        return targetName;
    }
}
