package de.mecrytv.nexusapi;

import de.mecrytv.databaseapi.DatabaseAPI;
import de.mecrytv.nexusapi.manager.CooldownManager;
import de.mecrytv.nexusapi.models.*;
import de.mecrytv.nexusapi.repository.RepoManager;
import de.mecrytv.nexusapi.utils.GlobalNotifyer;
import de.mecrytv.nexusapi.utils.HistoryHelper;

public class NexusAPI {

    private static NexusAPI instance;
    private RepoManager repoManager;
    private HistoryHelper historyHelper;
    private GlobalNotifyer globalNotifyer;
    private CooldownManager cooldownManager;

    private NexusAPI() {}

    public static NexusAPI getInstance() {
        if (instance == null) {
            instance = new NexusAPI();
        }
        return instance;
    }

    public void init() {
        registerModels();
        this.repoManager = new RepoManager();
        this.historyHelper = new HistoryHelper();
        this.globalNotifyer = new GlobalNotifyer();
        this.cooldownManager = new CooldownManager();
    }

    private void registerModels() {
        DatabaseAPI db = DatabaseAPI.getInstance();
        db.registerModel("reportteleport", TeleportModel::new);
        db.registerModel("punishments", PunishmentHistoryModel::new);
        db.registerModel("ban", BanModel::new);
        db.registerModel("mute", MuteModel::new);
        db.registerModel("warn", WarnModel::new);
    }

    public RepoManager getRepoManager() {
        return repoManager;
    }
    public HistoryHelper getHistoryHelper() { return historyHelper; }
    public GlobalNotifyer getGlobalNotifyer() { return globalNotifyer; }
    public CooldownManager getCooldownManager() { return cooldownManager; }
}