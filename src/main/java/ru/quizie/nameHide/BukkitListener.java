package ru.quizie.nameHide;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;
import ru.quizie.nameHide.displays.DisplayManager;

public class BukkitListener implements Listener {

    private final NameHide plugin;

    public BukkitListener(NameHide plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    private void on(PlayerJoinEvent event) {
        DisplayManager.create(event.getPlayer(), plugin);
    }

    @EventHandler
    private void on(PlayerQuitEvent event) {
        DisplayManager.remove(event.getPlayer());
    }

    @EventHandler
    private void on(PlayerDeathEvent event) {
        DisplayManager.remove(event.getPlayer());
    }

    @EventHandler
    private void on(PlayerTeleportEvent event) {
        DisplayManager.remove(event.getPlayer());
        DisplayManager.create(event.getPlayer(), plugin);
    }


    @EventHandler
    private void on(PlayerRespawnEvent event) {
        DisplayManager.create(event.getPlayer(), plugin);
    }
}
