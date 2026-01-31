package de.mecrytv.nexusapi.utils;

public class InterfaceUtils {

    public interface PlatformScheduler {
        void runTask(Runnable runnable);
    }
}
