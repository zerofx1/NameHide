package ru.quizie.nameHide.services;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import ru.quizie.nameHide.displays.DisplayManager;

public class UpdateDisplayService extends BukkitRunnable {

    @Override
    public void run() {
        Bukkit.getOnlinePlayers().forEach(DisplayManager::updateDisplay);
    }
}
