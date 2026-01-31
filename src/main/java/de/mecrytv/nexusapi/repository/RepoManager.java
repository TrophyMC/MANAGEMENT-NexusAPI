package de.mecrytv.nexusapi.repository;

import de.mecrytv.nexusapi.models.*;

public class RepoManager {

    private final NexusRepository<BanModel> banRepository;
    private final NexusRepository<MuteModel> muteRepository;
    private final NexusRepository<WarnModel> warnRepository;
    private final NexusRepository<TeleportModel> teleportRepository;
    private final NexusRepository<PunishmentHistoryModel> historyRepository;

    public RepoManager() {
        this.banRepository = new NexusRepository<>("ban");
        this.muteRepository = new NexusRepository<>("mute");
        this.warnRepository = new NexusRepository<>("warn");
        this.teleportRepository = new NexusRepository<>("reportteleport");
        this.historyRepository = new NexusRepository<>("punishments");
    }

    public NexusRepository<BanModel> getBanRepository() { return banRepository; }
    public NexusRepository<MuteModel> getMuteRepository() { return muteRepository; }
    public NexusRepository<WarnModel> getWarnRepository() { return warnRepository; }
    public NexusRepository<TeleportModel> getTeleportRepository() { return teleportRepository; }
    public NexusRepository<PunishmentHistoryModel> getHistoryRepository() { return historyRepository; }
}