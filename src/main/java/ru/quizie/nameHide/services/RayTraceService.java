package ru.quizie.nameHide.services;

import org.bukkit.Bukkit;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import ru.quizie.nameHide.Config;
import ru.quizie.nameHide.NameHide;
import ru.quizie.nameHide.displays.DisplayManager;
import ru.quizie.nameHide.displays.data.DisplayData;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RayTraceService extends BukkitRunnable {

    private final NameHide plugin;
    private final Map<UUID, Map<UUID, Boolean>> visibilityCache;

    public RayTraceService(NameHide plugin) {
        this.plugin = plugin;
        this.visibilityCache = new HashMap<>();
    }

    @Override
    public void run() {
        for (Player viewer : Bukkit.getOnlinePlayers()) {
            final UUID viewerId = viewer.getUniqueId();
            visibilityCache.putIfAbsent(viewerId, new HashMap<>());

            final Map<UUID, Boolean> viewerVisibilityCache = visibilityCache.get(viewerId);
            final Location viewerEyeLocation = viewer.getEyeLocation();

            for (Player target : Bukkit.getOnlinePlayers()) {
                if (target.equals(viewer)) continue;

                final UUID targetId = target.getUniqueId();
                final boolean visible = this.getVisibility(viewerEyeLocation, target);

                updateVisibility(viewer, target, targetId, viewerVisibilityCache, visible);
            }
        }
    }

    private boolean getVisibility(Location viewerEyeLocation, Player target) {
        final Location targetEyeLocation = target.getEyeLocation();
        final double distance = viewerEyeLocation.distance(targetEyeLocation);

        if (distance > Config.radius) return false;

        return viewerEyeLocation.getWorld().rayTraceBlocks(
                viewerEyeLocation,
                targetEyeLocation.toVector().subtract(viewerEyeLocation.toVector()).normalize(),
                distance,
                FluidCollisionMode.NEVER,
                true
        ) == null;
    }

    private void updateVisibility(Player viewer, Player target, UUID targetId, Map<UUID, Boolean> viewerVisibilityCache, boolean visible) {
        final DisplayData displayDataViewer = DisplayManager.getDispalyData(viewer);
        if (displayDataViewer == null) return;

        final Boolean oldVisible = viewerVisibilityCache.get(targetId);
        if (oldVisible == null || oldVisible != visible) {
            if (visible) {
                target.showEntity(plugin, displayDataViewer.getTextDisplay());
            } else {
                target.hideEntity(plugin, displayDataViewer.getTextDisplay());
            }

            viewerVisibilityCache.put(targetId, visible);
        }
    }
}
